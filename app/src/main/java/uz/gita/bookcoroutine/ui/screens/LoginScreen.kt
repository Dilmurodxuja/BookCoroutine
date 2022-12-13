package uz.gita.bookcoroutine.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.databinding.ScreenLoginBinding
import uz.gita.bookcoroutine.presentation.viewmodel.LoginViewModel
import uz.gita.bookcoroutine.presentation.viewmodel.impl.LoginViewModelImpl
import uz.gita.bookcoroutine.utils.amount
import uz.gita.bookcoroutine.utils.showToast
import uz.gita.bookcoroutine.utils.state

class LoginScreen : Fragment(R.layout.screen_login) {
    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
        viewModel.openVerifyScreenLiveData.observe(this, openVerifyScreenObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.buttonEnableLiveData.observe(this, buttonEnableObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observers()
        clickEvents()
    }

    private fun observers() { viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver) }

    private fun clickEvents() { binding.apply {
            buttonSubmit.setOnClickListener { viewModel.login(inputPhone.amount(), inputPassword.amount()) }
            buttonRegister.setOnClickListener { viewModel.openRegisterScreen() } }
    }

    private val openRegisterScreenObserver = Observer<Unit> { navController.navigate(R.id.action_loginScreen_to_registerScreen) }
    private val openVerifyScreenObserver = Observer<Unit> { navController.navigate(R.id.action_loginScreen_to_signInVerifyScreen) }
    private val progressObserver = Observer<Boolean> { binding.progress.state(it) }
    private val buttonEnableObserver = Observer<Boolean> { binding.apply { buttonSubmit.isEnabled = it;buttonRegister.isEnabled = it } }
    private val errorObserver = Observer<String> { showToast(it) }
}