package uz.gita.bookcoroutine.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.bookcoroutine.data.remote.request.AuthRequest

interface RegisterViewModel {
    fun back()
    fun registerUser(signUp: AuthRequest.SignUp)

    val backLivedata: LiveData<Unit>
    val openVerifyScreenLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
}
