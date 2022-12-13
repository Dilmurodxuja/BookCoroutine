package uz.gita.bookcoroutine.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.databinding.ScreenDashboardBinding
import uz.gita.bookcoroutine.presentation.viewmodel.DashboardViewModel
import uz.gita.bookcoroutine.presentation.viewmodel.impl.DashboardViewModelImpl
import uz.gita.bookcoroutine.ui.adapters.BookAdapter
import uz.gita.bookcoroutine.ui.dialogs.AddBookDialog
import uz.gita.bookcoroutine.utils.showToast
import uz.gita.bookcoroutine.utils.state

class Dashboard: Fragment(R.layout.screen_dashboard) {

    private val binding by viewBinding(ScreenDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels<DashboardViewModelImpl>()
    private val adapter: BookAdapter = BookAdapter()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.addLiveData.observe(this, openAddDialogObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openEditDialogLiveData.observe(this, openEditDialogObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterSettings()
        clickEvents()
        subscribers()
    }

    private fun adapterSettings() {
        binding.recView.adapter = adapter
        binding.recView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun clickEvents() {
        adapter.setMoreButton{data, view-> showPopUpMenu(view, onEdit = { viewModel.openEditDialog(data) }, onDelete = { viewModel.deleteBook(data) })}
        adapter.setLikeButton{viewModel.favouriteBook(it)}
        binding.apply { buttonAdd.setOnClickListener { viewModel.openAddDialog()}
        buttonLogOut.setOnClickListener { viewModel.logout() }
        buttonSync.setOnClickListener { viewModel.sync() } }
    }

    private fun subscribers() {
        viewModel.allBooksLiveData.observe(viewLifecycleOwner, allBooksObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.backLiveData.observe(viewLifecycleOwner, backObserver)
        viewModel.logoutLiveData.observe(viewLifecycleOwner, logoutObserver)
    }

    private val allBooksObserver = Observer<List<BookEntity>> {
        adapter.submitList(it)
        Log.d("TTT", "list size: ${it.size}")
    }
    private val openAddDialogObserver = Observer<Unit> {
        val dialog = AddBookDialog()
        dialog.setSaveBookListener { viewModel.addBook(it) }
        dialog.show(childFragmentManager, "addBookDialog")
        viewModel.closeDialogLiveData.observe(viewLifecycleOwner){dialog.closeDialog()}
    }

    private val openEditDialogObserver = Observer<BookEntity> { old->
        val dialog = AddBookDialog()
        dialog.setData(old)
        viewModel.closeDialogLiveData2.observe(viewLifecycleOwner){dialog.closeDialog()}
        dialog.setUpdateBookListener { viewModel.updateBook(it) }
        dialog.show(childFragmentManager, "editBookDialog")
    }

    private val errorObserver = Observer<String> { showToast(it);  binding.progress.hide()}
    private val progressObserver = Observer<Boolean> { binding.progress.state(it)}
    private val backObserver = Observer<Unit> { /*navController.navigate(R.id.loginScreen)*/ }
    private val logoutObserver = Observer<Unit> { navController.popBackStack(R.id.loginScreen, false) }
}