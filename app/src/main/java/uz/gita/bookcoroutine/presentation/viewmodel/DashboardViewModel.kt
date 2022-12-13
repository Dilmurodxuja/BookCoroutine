package uz.gita.bookcoroutine.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest


interface DashboardViewModel{
    val closeDialogLiveData: LiveData<Unit>
    val closeDialogLiveData2: LiveData<Unit>
    val progressLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val allBooksLiveData: LiveData<List<BookEntity>>
    val openEditDialogLiveData: LiveData<BookEntity>
    val addLiveData: LiveData<Unit>
    val backLiveData: LiveData<Unit>
    val logoutLiveData: LiveData<Unit>

    fun updateBook(book: BookRequest.Update)
    fun openAddDialog()
    fun openEditDialog(data: BookEntity)
    fun addBook(book: BookRequest.Add)
    fun logout()
    fun deleteBook(book: BookEntity)
    fun favouriteBook(book: BookEntity)
    fun sync()
}