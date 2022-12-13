package uz.gita.bookcoroutine.ui.screens.verify.signIn

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.ui.screens.verify.BaseVerifyScreen

class SignInVerifyScreen : BaseVerifyScreen() {
    override val viewModel: SignInVerifyViewModel by viewModels<SignInVerifyViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated() {
        viewModel.toBooksScreenLivedata.observe(viewLifecycleOwner, toBookScreenObserver)
    }
    private val toBookScreenObserver = Observer<Unit> { navController.navigate(R.id.action_signInVerifyScreen_to_dashboard) }

}