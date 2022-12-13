package uz.gita.bookcoroutine.presentation.usecases

import kotlinx.coroutines.flow.Flow

interface SplashUseCase {
    fun openScreen(): Flow<Int>

}
