package uz.gita.bookcoroutine.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.databinding.ScreenRegisterBinding
import uz.gita.bookcoroutine.presentation.viewmodel.impl.RegisterViewModelImpl
import uz.gita.bookcoroutine.presentation.viewmodel.RegisterViewModel
import uz.gita.bookcoroutine.utils.amount
import uz.gita.bookcoroutine.utils.showToast

class RegisterScreen: Fragment(R.layout.screen_register) {
    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openVerifyScreenLiveData.observe(this, openVerifyScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clickEvents()
        observers()
    }

    private fun observers() {
        viewModel.backLivedata.observe(viewLifecycleOwner, backObserver)
    }

    private fun clickEvents() = with(binding){
        backButton.setOnClickListener { viewModel.back() }
        buttonRegister.setOnClickListener { viewModel.registerUser(
            AuthRequest.SignUp(inputPhone.amount(), inputConfirmPassword.amount(), lastName.amount(), firstName.amount())) }
        inputConfirmPassword.addTextChangedListener { buttonRegister.isEnabled = it.toString() == inputPassword.text.toString() }
    }

    private val errorObserver = Observer<String?> { showToast(it) }
    private val openVerifyScreenObserver = Observer<Unit> { navController.navigate(R.id.action_registerScreen_to_registerVerifyScreen)}
    private val backObserver = Observer<Unit> { navController.popBackStack()}
}