package uz.gita.bookcoroutine.ui.screens.verify.signUp

import android.os.CountDownTimer
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.domain.repository.AuthRepository
import uz.gita.bookcoroutine.domain.repository.AuthRepositoryImpl
import uz.gita.bookcoroutine.utils.isConnected
import java.text.DecimalFormat

class RegisterVerifyViewModelImpl: ViewModel(), RegisterVerifyViewModel {
    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()

    override val progressLiveData = MutableLiveData<Boolean>()
    override val backLivedata = MutableLiveData<Unit>()
    override val registeredLivedata = MutableLiveData<Unit>()
    override val timerLivedata = MutableLiveData<String>()
    override val timerFinishLivedata = MutableLiveData<String>()
    override val errorLiveData = MediatorLiveData<String>()

    init {
        timerStart()
    }

    override fun back() {
        backLivedata.value = Unit
    }

    override fun submitSms(code: String) {
        if (!isConnected()) { errorLiveData.value = "Not connection!";return }
        progressLiveData.value = true
        progressLiveData.value = true
        viewModelScope.launch {
            repository.signUpVerify(AuthRequest.Verify(code)).onEach {
                it.onSuccess { registeredLivedata.postValue(Unit) }
                it.onFailure {fail-> errorLiveData.postValue(fail.message) }
                progressLiveData.postValue(false)
            }.collect()
        }
    }

    override fun timerStart() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val f = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                timerLivedata.value = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec)
            }
            override fun onFinish() {
                timerFinishLivedata.value = "Resend code"
            }
        }
        timer.start()
    }
}