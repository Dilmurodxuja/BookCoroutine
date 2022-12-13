package uz.gita.bookcoroutine.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookcoroutine.data.local.LocalStorage
import uz.gita.bookcoroutine.presentation.usecases.SplashUseCase
import uz.gita.bookcoroutine.presentation.usecases.impl.SplashUseCaseImpl
import uz.gita.bookcoroutine.presentation.viewmodel.SplashViewModel

class SplashViewModelImpl : ViewModel(), SplashViewModel {
    private val splashUseCase: SplashUseCase = SplashUseCaseImpl(LocalStorage.getInstance())

    override val openLoginScreenLiveData = MutableLiveData<Unit>()
    override val openBooksScreenLiveData = MutableLiveData<Unit>()

    init {
        openNextScreen()
    }

    private fun openNextScreen() {
        splashUseCase.openScreen().onEach {
            if (it == 1) openLoginScreenLiveData.postValue(Unit)
            else openBooksScreenLiveData.postValue(Unit)
        }.launchIn(viewModelScope)
    }

}