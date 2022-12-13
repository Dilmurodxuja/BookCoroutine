package uz.gita.bookcoroutine.ui.screens

import android.view.View
import androidx.appcompat.widget.PopupMenu
import uz.gita.bookcoroutine.R

internal fun Dashboard.showPopUpMenu(view: View, onEdit: () -> Unit, onDelete: () -> Unit) {
    val popUpMenu = PopupMenu(requireContext(), view)
    popUpMenu.inflate(R.menu.popup_menu)
    popUpMenu.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.edit -> onEdit()
            R.id.delete -> onDelete()
        }
        true
    }
    popUpMenu.show()
}