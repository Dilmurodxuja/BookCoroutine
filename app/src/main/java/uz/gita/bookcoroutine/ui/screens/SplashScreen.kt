package uz.gita.bookcoroutine.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.presentation.viewmodel.SplashViewModel
import uz.gita.bookcoroutine.presentation.viewmodel.impl.SplashViewModelImpl

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.apply {
            openLoginScreenLiveData.observe(viewLifecycleOwner, openLoginScreenObserver)
            openBooksScreenLiveData.observe(viewLifecycleOwner, openBooksScreenObserver)
        }
    }

    private val openLoginScreenObserver = Observer<Unit> {
        navController.navigate(R.id.action_splashScreen_to_loginScreen)
    }
    private val openBooksScreenObserver = Observer<Unit> {
        navController.navigate(R.id.action_splashScreen_to_dashboard)
    }
}