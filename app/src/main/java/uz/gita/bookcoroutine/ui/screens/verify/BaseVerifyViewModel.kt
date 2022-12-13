package uz.gita.bookcoroutine.ui.screens.verify

import androidx.lifecycle.LiveData

interface BaseVerifyViewModel {
    val errorLiveData: LiveData<String>
    val backLivedata: LiveData<Unit>
    val timerLivedata: LiveData<String>
    val timerFinishLivedata: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun back()
    fun submitSms(code: String)
    fun timerStart()
}