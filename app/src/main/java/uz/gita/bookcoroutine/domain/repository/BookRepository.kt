package uz.gita.bookcoroutine.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest.*

interface BookRepository {
    fun getOfflineData(): Flow<Result<List<BookEntity>>>
    fun getFromServer(): Flow<Result<List<BookEntity>>>

    fun addToServer(book: Add): Flow<Result<BookEntity>>
    fun addOffline(data: Add): Flow<Result<BookEntity>>

    fun updateInServer(data: Update): Flow<String>
    fun updateInOffline(data: Update): Flow<String>

    fun deleteFromServer(book: BookEntity): Flow<String>
    fun deleteFromOffline(book: BookEntity): Flow<String>

    fun changeFavourite(book: BookEntity): Flow<String>

    fun sync(viewModelScope: CoroutineScope)

    suspend fun logout()
}