package uz.gita.bookcoroutine.data.remote.request

import com.google.gson.annotations.SerializedName

sealed class BookRequest {
    data class Add(
        @SerializedName("title")
        val title: String,
        @SerializedName("author")
        val author: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("pageCount")
        val pageCount: Int
    )

    data class Update(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("author")
        val author: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("pageCount")
        val pageCount: Int
    )

    data class ById(
        @SerializedName("bookId")
        val bookId: Int
    )
    data class ChangeFav(
        @SerializedName("bookId")
        val bookId: Int,
        @SerializedName("isLike")
        val isLike: Boolean
    )
}