package uz.gita.bookcoroutine.data.remote.request

import com.google.gson.annotations.SerializedName

data class UserId(
    @SerializedName("userId")
    val userId: Int
)
