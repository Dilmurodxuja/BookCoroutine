package uz.gita.bookcoroutine.presentation.usecases.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest
import uz.gita.bookcoroutine.domain.repository.BookRepository
import uz.gita.bookcoroutine.presentation.usecases.DashboardUseCase

class DashboardUseCaseImpl(private val repository: BookRepository): DashboardUseCase {
    override fun getBooks(hasConnection: Boolean): Flow<Result<List<BookEntity>>> =
        if(hasConnection) repository.getFromServer() else repository.getOfflineData()

    override fun add(book: BookRequest.Add, hasConnection: Boolean):Flow<Result<BookEntity>>  =
        if (hasConnection) repository.addToServer(book) else repository.addOffline(book)

    override fun update(book: BookRequest.Update, connected: Boolean): Flow<String> =
        if (connected) repository.updateInServer(book) else repository.updateInOffline(book)

    override fun changeFavourite(book: BookEntity): Flow<String> =
        repository.changeFavourite(book)

    override fun delete(book: BookEntity, connected: Boolean): Flow<String> =
        if (connected) repository.deleteFromServer(book) else repository.deleteFromOffline(book)

    override fun sync(viewModelScope: CoroutineScope) =
        repository.sync(viewModelScope)

    override suspend fun logout() =
        repository.logout()
}
