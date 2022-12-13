package uz.gita.bookcoroutine.ui.screens.verify.signIn

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.domain.repository.AuthRepository
import uz.gita.bookcoroutine.domain.repository.AuthRepositoryImpl
import uz.gita.bookcoroutine.utils.*
import java.text.DecimalFormat

class SignInVerifyViewModelImpl: ViewModel(), SignInVerifyViewModel {

    private val authRepository: AuthRepository = AuthRepositoryImpl.getInstance()

    override val errorLiveData = MutableLiveData<String>()
    override val backLivedata = MutableLiveData<Unit>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val timerLivedata = MutableLiveData<String>()
    override val timerFinishLivedata = MutableLiveData<String>()
    override val toBooksScreenLivedata = MutableLiveData<Unit>()

    init {
        timerStart()
    }

    override fun back() {
        backLivedata.value = Unit
    }

    override fun submitSms(code: String) {
        if (!isConnected()) { errorLiveData.value = "Not connection!"
            return }
        progressLiveData.value = true
        viewModelScope.launch {
            authRepository.signInVerify(AuthRequest.Verify(code)).onEach {
                it.onSuccess { toBooksScreenLivedata.value = Unit }
                it.onFailure { errorLiveData.value = it.message }
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
            override fun onFinish() { timerFinishLivedata.value = "Resend code" }
        }
        timer.start()
    }
}