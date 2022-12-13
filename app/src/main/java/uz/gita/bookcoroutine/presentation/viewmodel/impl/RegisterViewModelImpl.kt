package uz.gita.bookcoroutine.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.domain.repository.AuthRepository
import uz.gita.bookcoroutine.domain.repository.AuthRepositoryImpl
import uz.gita.bookcoroutine.presentation.viewmodel.RegisterViewModel

class RegisterViewModelImpl : ViewModel(), RegisterViewModel {
    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()

    override fun back() {
    }

    override fun registerUser(signUp: AuthRequest.SignUp) {
        viewModelScope.launch {
            repository.signUp(signUp).onEach {
                it.onSuccess { openVerifyScreenLiveData.postValue(Unit) }
                it.onFailure { errorLiveData.postValue(it.message) }
            }.collect()
        }
    }

    override val backLivedata = MutableLiveData<Unit>()
    override val openVerifyScreenLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<String>()
}
