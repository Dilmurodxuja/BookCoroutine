package uz.gita.bookcoroutine.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest
import uz.gita.bookcoroutine.databinding.DialogEditBookBinding
import uz.gita.bookcoroutine.utils.amount

class AddBookDialog : DialogFragment(R.layout.dialog_edit_book) {
    private var saveBookListener: ((BookRequest.Add) -> Unit)? = null
    private var updateBookListener: ((BookRequest.Update) -> Unit)? = null
    private val binding by viewBinding(DialogEditBookBinding::bind)
    private var data: BookEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            cancelButton.setOnClickListener { dismiss() }
            saveButton.setOnClickListener {
                it.isEnabled = false
                if (data == null) saveBookListener?.invoke(
                    BookRequest.Add(
                        title.amount(),
                        author.amount(),
                        description.amount(),
                        pages.amount().toInt()
                    )
                )
                else updateBookListener?.invoke(
                    BookRequest.Update(
                        data!!.id,
                        title.amount(),
                        author.amount(),
                        description.amount(),
                        pages.amount().toInt()
                    )
                )
                dismiss()
            }
        }
        showEditData()
        checkButtonStatus()
    }

    private fun checkButtonStatus() {
        binding.apply {
            pages.addTextChangedListener {
                saveButton.isEnabled = it?.isNotEmpty()!! && title.length() > 3 && description.length() > 5 && author.length() > 5
            }
            data?.let {
                checkViewEdited(title, it.title)
                checkViewEdited(author, it.author)
                checkViewEdited(description, it.description)
                checkViewEdited(pages, it.pageCount.toString())
            } }
    }

    private fun checkViewEdited(item: AppCompatEditText, item2: String) {
        item.addTextChangedListener { v->
            binding.saveButton.isEnabled = v.toString() != item2
        }
    }

    private fun showEditData() {
        data?.let {
            binding.apply {
                title.setText(data?.title)
                description.setText(data?.description)
                data?.pageCount?.let { pages.setText("$it") }
                author.setText(data?.author)
            }
        }
    }

    fun setSaveBookListener(block: (BookRequest.Add) -> Unit) {
        saveBookListener = block
    }

    fun setUpdateBookListener(block: (BookRequest.Update) -> Unit) {
        updateBookListener = block
    }

    fun setData(_data: BookEntity?) {
        _data?.let { data = it }
    }

    fun closeDialog() {
        dismiss()
    }
}


