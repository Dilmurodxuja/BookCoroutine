package uz.gita.bookcoroutine.presentation.viewmodel

import androidx.lifecycle.LiveData


interface SplashViewModel{
    val openLoginScreenLiveData: LiveData<Unit>
    val openBooksScreenLiveData: LiveData<Unit>
}