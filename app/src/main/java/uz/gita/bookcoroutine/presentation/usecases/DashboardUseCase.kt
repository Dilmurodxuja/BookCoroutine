package uz.gita.bookcoroutine.presentation.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest

interface DashboardUseCase {
    fun getBooks(hasConnection: Boolean): Flow<Result<List<BookEntity>>>
    fun add(book: BookRequest.Add, hasConnection: Boolean): Flow<Result<BookEntity>>
    fun delete(book: BookEntity, connected: Boolean): Flow<String>
    fun update(book: BookRequest.Update, connected: Boolean): Flow<String>

    fun changeFavourite(book: BookEntity): Flow<String>
    fun sync(viewModelScope: CoroutineScope)

    suspend fun logout()
}
