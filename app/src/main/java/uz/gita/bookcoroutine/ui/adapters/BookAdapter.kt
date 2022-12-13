package uz.gita.bookcoroutine.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bookcoroutine.R
import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.databinding.ItemBookBinding
import uz.gita.bookcoroutine.utils.ChangeCol

class BookAdapter : ListAdapter<BookEntity, BookAdapter.ContactViewHolder>(ContactDiffUtil){
    private var moreButton: ((BookEntity, View) -> Unit)? = null
    private var likeButton: ((BookEntity) -> Unit)? = null

    object ContactDiffUtil : DiffUtil.ItemCallback<BookEntity>() {
        override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean = oldItem.author == newItem.author
            && oldItem.title == newItem.title && oldItem.fav == newItem.fav && oldItem.synCol == newItem.synCol && oldItem.description == newItem.description
    }

    inner class ContactViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                buttonMore.setOnClickListener { moreButton?.invoke(getItem(bindingAdapterPosition), it) }
                buttonLike.setOnClickListener { likeButton?.invoke(getItem(bindingAdapterPosition)) }
            }
        }

        fun bind() = with(binding){
            getItem(absoluteAdapterPosition).apply {
                textTitle.text = title
                textAuthor.text = author
                textPage.text = pageCount.toString()
                buttonLike.setImageResource(if (this.fav) R.drawable.ic_star_ else R.drawable.ic_star_off)
                buttonSync.visibility = if(synCol != ChangeCol.NO_CHANGE.value) View.VISIBLE else View.GONE
            }
            return@with
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind()

    fun setMoreButton(block: (BookEntity, View) -> Unit) { moreButton = block }

    fun setLikeButton(block: (BookEntity) -> Unit) { likeButton = block }
}