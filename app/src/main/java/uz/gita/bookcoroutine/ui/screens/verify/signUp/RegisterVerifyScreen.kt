package uz.gita.bookcoroutine.ui.screens.verify.signUp

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.ui.screens.verify.BaseVerifyScreen

class RegisterVerifyScreen : BaseVerifyScreen() {
    override val viewModel: RegisterVerifyViewModel by viewModels<RegisterVerifyViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated() {
        viewModel.registeredLivedata.observe(this, registeredObserver)
    }
    private val registeredObserver = Observer<Unit>{
        navController.navigate(R.id.action_registerVerifyScreen_to_dashboard)
    }
}