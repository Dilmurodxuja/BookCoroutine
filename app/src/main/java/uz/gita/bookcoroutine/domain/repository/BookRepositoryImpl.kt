package uz.gita.bookcoroutine.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import uz.gita.bookcoroutine.data.local.LocalStorage
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.local.room.MyDatabase
import uz.gita.bookcoroutine.data.remote.ApiClient
import uz.gita.bookcoroutine.data.remote.apis.BookApi
import uz.gita.bookcoroutine.data.remote.request.BookRequest.*
import uz.gita.bookcoroutine.utils.*

class BookRepositoryImpl(private val bookApi: BookApi, private val localStorage: LocalStorage) : BookRepository {

    companion object {
        private var repository: BookRepository? = null

        fun getInstance() = if (repository != null) repository!! else {
            val api = ApiClient.retrofit.create(BookApi::class.java)
            val localStorage = LocalStorage.getInstance()
            val temp = BookRepositoryImpl(api, localStorage)
            repository = temp
            temp
        }
    }

    private val bookDao = MyDatabase.getInstance().getBookDao()
    private val tokenB
        get() = "Bearer ${localStorage.token}"

    private fun currentOfflineData(): List<BookEntity> = bookDao.getAllExistsBooks()

    override fun getOfflineData(): Flow<Result<List<BookEntity>>> = flow {
        emit(Result.success(currentOfflineData()))
    }.flowOn(Dispatchers.IO)

    override fun getFromServer(): Flow<Result<List<BookEntity>>> = flow {
        val response = bookApi.getAllBooks(tokenB)
        if (response.isSuccessful) response.body()?.let {
            emit(Result.success(it.map { it.toEntity(ChangeCol.NO_CHANGE) }))
        }
        else emit(Result.failure(Exception(showApiError(response))))
        return@flow
    }.flowOn(Dispatchers.IO)

    override fun addOffline(data: Add): Flow<Result<BookEntity>> = flow {
        bookDao.insert(data.toEntity(ChangeCol.ADDED))
        Result.success(data)
    }

    override fun addToServer(book: Add): Flow<Result<BookEntity>> = flow {
        val response = bookApi.addBook(tokenB, book)
        if (response.isSuccessful)
            response.body()?.let {
                val bookResponse = it.toEntity(ChangeCol.NO_CHANGE)
                bookDao.insert(bookResponse)
                emit(Result.success(bookResponse))
            }
        else emit(Result.failure(Exception(showApiError(response))))
        return@flow
    }.flowOn(Dispatchers.IO)

    override fun updateInOffline(data: Update): Flow<String> = flow {
        bookDao.update(data.toBookEntity(ChangeCol.UPDATED))
        emit("Updated to offline base only")
    }.flowOn(Dispatchers.IO)

    override fun updateInServer(data: Update): Flow<String> = flow {
        val response = bookApi.updateBook(tokenB, data)
        if (response.isSuccessful)
            response.body()?.let {
                bookDao.update(data.toBookEntity(ChangeCol.NO_CHANGE))
                emit("Updated in api")
            }
        else emit(showApiError(response))
    }.flowOn(Dispatchers.IO)

    override fun deleteFromOffline(book: BookEntity): Flow<String> = flow {
        bookDao.update(book.toBookEntity(ChangeCol.DELETED))
        delay(1)
        emit("Deleted from offline")
    }.flowOn(Dispatchers.IO)

    override fun deleteFromServer(book: BookEntity): Flow<String> = flow {
        val response = bookApi.deleteBook(tokenB, book.toById())
        if (response.isSuccessful)
            response.body()?.let {
                bookDao.delete(book)
                emit(it.message)
            }
        else emit(showApiError(response))
    }.flowOn(Dispatchers.IO)

    override fun changeFavourite(book: BookEntity): Flow<String> = flow {
        val response = bookApi.updateBookFav(tokenB, book.toById())
        if (response.isSuccessful) response.body()?.let { emit(it.message) }
    }

    private suspend fun deleteAllData() {
        bookDao.getAllBooks().onEach { it.forEach { bookDao.delete(it) } }.collect()
    }

    override fun sync(viewModelScope: CoroutineScope) {
        if (!isConnected()) {
//            messageLiveData.postValue("There is no internet connection yet")
            return
        }
        val offlineAdded = bookDao.getOfflineData(ChangeCol.ADDED.value)
        val offlineUpdated = bookDao.getOfflineData(ChangeCol.UPDATED.value)
        val offlineDeleted = bookDao.getOfflineData(ChangeCol.DELETED.value)

        offlineAdded.onEach {
            it.forEach { book ->
                addToServer(book.toAddRequest())
                bookDao.update(book.toBookEntity(ChangeCol.NO_CHANGE))
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)

        offlineUpdated.onEach {
            it.forEach { book ->
                updateInServer(book.toUpdate())
                bookDao.update(book.toBookEntity(ChangeCol.NO_CHANGE))
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)

        offlineDeleted.onEach {
            it.forEach { book ->
                deleteFromServer(book)
                bookDao.delete(book)
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    override suspend fun logout() {
        localStorage.startScreen = "login"
        localStorage.token = ""
        deleteAllData()
    }
}


