package uz.gita.bookcoroutine.ui.screens.verify.signUp

import androidx.lifecycle.MutableLiveData
import uz.gita.bookcoroutine.ui.screens.verify.BaseVerifyViewModel

interface RegisterVerifyViewModel : BaseVerifyViewModel {
    val registeredLivedata: MutableLiveData<Unit>
}