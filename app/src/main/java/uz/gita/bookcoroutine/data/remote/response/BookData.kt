package uz.gita.bookcoroutine.data.remote.response

import com.google.gson.annotations.SerializedName

data class BookData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("fav")
    val fav: Boolean = false
)