package uz.gita.bookcoroutine.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var author: String,
    var description: String,
    var pageCount: Int,
    var fav: Boolean = false,
    var synCol: Int = 1
)