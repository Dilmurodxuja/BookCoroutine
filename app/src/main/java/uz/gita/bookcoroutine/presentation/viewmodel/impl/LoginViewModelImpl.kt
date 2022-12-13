package uz.gita.bookcoroutine.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.domain.repository.AuthRepository
import uz.gita.bookcoroutine.domain.repository.AuthRepositoryImpl
import uz.gita.bookcoroutine.presentation.viewmodel.LoginViewModel
import uz.gita.bookcoroutine.utils.isConnected

class LoginViewModelImpl : ViewModel(), LoginViewModel {
    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val buttonEnableLiveData = MutableLiveData<Boolean>()
    override val openVerifyScreenLiveData = MutableLiveData<Unit>()
    override val openRegisterScreenLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<String>()
    private var loginJob: Job? = null

    override fun login(phone: String, password: String) {
        if (!isConnected()) {
            errorLiveData.value = "Not connection"
            return
        }
        progressLiveData.value = true
        buttonEnableLiveData.value = false
        loginJob = viewModelScope.launch {
            repository.signIn(AuthRequest.SignIn(phone, password)).onEach {
                it.onSuccess { openVerifyScreenLiveData.postValue(Unit) }
                it.onFailure { errorLiveData.postValue(it.message) }
                progressLiveData.postValue(false)
                buttonEnableLiveData.postValue(true)
            }.collect()
        }
    }

    override fun openRegisterScreen() {
        loginJob?.let { if(it.isActive) it.cancel() }
        openRegisterScreenLiveData.value = Unit
    }
}
