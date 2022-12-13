package uz.gita.bookcoroutine.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest.*
import uz.gita.bookcoroutine.domain.repository.BookRepositoryImpl
import uz.gita.bookcoroutine.presentation.usecases.DashboardUseCase
import uz.gita.bookcoroutine.presentation.usecases.impl.DashboardUseCaseImpl
import uz.gita.bookcoroutine.presentation.viewmodel.DashboardViewModel
import uz.gita.bookcoroutine.utils.isConnected

class DashboardViewModelImpl : ViewModel(), DashboardViewModel {
    private val dashboardUseCase: DashboardUseCase = DashboardUseCaseImpl(BookRepositoryImpl.getInstance())
    private val allList: ArrayList<BookEntity> = ArrayList()
    override val closeDialogLiveData = MutableLiveData<Unit>()
    override val closeDialogLiveData2 = MutableLiveData<Unit>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()
    override val addLiveData = MutableLiveData<Unit>()
    override val backLiveData = MutableLiveData<Unit>()
    override val logoutLiveData = MutableLiveData<Unit>()
    override val openEditDialogLiveData = MutableLiveData<BookEntity>()
    override val allBooksLiveData = MutableLiveData<List<BookEntity>>()

    init { load() }

    private fun load(){
        dashboardUseCase.getBooks(isConnected()).onEach {
            it.onSuccess { books ->
                allList.clear()
                allList.addAll(books)
                allBooksLiveData.value = books
            }
            it.onFailure { t -> errorLiveData.value = t.message }
        }.launchIn(viewModelScope)
    }

    override fun addBook(book: Add) {
        dashboardUseCase.add(book, isConnected()).onEach {
            it.onSuccess { book -> allList.add(book); allBooksLiveData.value = allList }
            it.onFailure { t -> errorLiveData.value = t.message }
        }.launchIn(viewModelScope)
    }

    override fun deleteBook(book: BookEntity) {
        dashboardUseCase.delete(book, isConnected()).onEach {
            allList.remove(book)
            allBooksLiveData.value = allList
            errorLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            dashboardUseCase.logout()
            logoutLiveData.value = Unit
        }
    }

    override fun updateBook(book: Update) {
        dashboardUseCase.update(book, isConnected()).onEach {
            errorLiveData.postValue(it); load()}.launchIn(viewModelScope)
    }

    override fun favouriteBook(book: BookEntity) {
        dashboardUseCase.changeFavourite(book).onEach { errorLiveData.value = it; load()}.launchIn(viewModelScope)
    }

    override fun sync() { dashboardUseCase.sync(viewModelScope) }

    override fun openAddDialog() { addLiveData.value = Unit }

    override fun openEditDialog(data: BookEntity) { openEditDialogLiveData.value = data }
}