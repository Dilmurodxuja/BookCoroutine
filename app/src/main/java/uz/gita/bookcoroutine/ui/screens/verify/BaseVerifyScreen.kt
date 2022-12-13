package uz.gita.bookcoroutine.ui.screens.verify

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.databinding.ScreenCodeVerifyBinding
import uz.gita.bookcoroutine.utils.amount
import uz.gita.bookcoroutine.utils.showToast
import uz.gita.bookcoroutine.utils.state

abstract class BaseVerifyScreen : Fragment(R.layout.screen_code_verify) {
    private val binding by viewBinding(ScreenCodeVerifyBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    abstract val viewModel: BaseVerifyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.timerFinishLivedata.observe(this, timerFinishObserver)
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clickEvents()
        observers()
        onViewCreated()
    }

    abstract fun onViewCreated()

    private fun observers() {
        viewModel.apply {
            progressLiveData.observe(viewLifecycleOwner, progressObserver)
            backLivedata.observe(viewLifecycleOwner, backObserver)
            timerLivedata.observe(viewLifecycleOwner, timerObserver)
        }
    }

    private fun clickEvents() = with(binding) {
        btnBack.setOnClickListener { viewModel.back() }
        counter.setOnClickListener { viewModel.timerStart() }
        btnSubmit.setOnClickListener { viewModel.submitSms(inputSmsCode.amount()) }
        inputSmsCode.addTextChangedListener { btnSubmit.isEnabled = it?.length == 6 }
    }

    private val progressObserver = Observer<Boolean> { binding.progress.state(it) }
    private val errorObserver = Observer<String> { showToast(it) }
    private val timerObserver = Observer<String> {
        binding.counter.text = it
        binding.counter.isClickable = false
    }
    private val timerFinishObserver = Observer<String> {
        binding.counter.apply {
            text = it
            isClickable = true
        }
    }
    private val backObserver = Observer<Unit> { navController.popBackStack() }
}
