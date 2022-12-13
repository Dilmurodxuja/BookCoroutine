package uz.gita.bookcoroutine.ui.screens.verify.signIn

import androidx.lifecycle.LiveData
import uz.gita.bookcoroutine.ui.screens.verify.BaseVerifyViewModel

interface SignInVerifyViewModel : BaseVerifyViewModel {
    val toBooksScreenLivedata: LiveData<Unit>
}