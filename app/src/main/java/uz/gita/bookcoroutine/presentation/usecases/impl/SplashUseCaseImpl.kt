package uz.gita.bookcoroutine.presentation.usecases.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.bookcoroutine.data.local.LocalStorage
import uz.gita.bookcoroutine.presentation.usecases.SplashUseCase

class SplashUseCaseImpl(private val localStorage: LocalStorage) : SplashUseCase {
    override fun openScreen(): Flow<Int> = flow {
        delay(1200)
        if (localStorage.startScreen == "login")  emit(1)
        else emit(0)
    }
}
