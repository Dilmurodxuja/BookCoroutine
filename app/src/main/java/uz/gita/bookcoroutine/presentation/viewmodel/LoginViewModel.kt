package uz.gita.bookcoroutine.presentation.viewmodel

import androidx.lifecycle.LiveData

interface LoginViewModel {
    fun login(phone: String, password: String)
    fun openRegisterScreen()

    val progressLiveData: LiveData<Boolean>
    val openVerifyScreenLiveData: LiveData<Unit>
    val openRegisterScreenLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val buttonEnableLiveData: LiveData<Boolean>
}
