package uz.gita.bookcoroutine.data.remote.request

import com.google.gson.annotations.SerializedName

sealed class AuthRequest {
    data class SignUp(
        @SerializedName("phone")
        val phone: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("firstName")
        val firstName: String
    )

    data class Verify(
        @SerializedName("code")
        val code: String
    )

    data class SignIn(
        @SerializedName("phone")
        val phone: String,
        @SerializedName("password")
        val password: String
    )
}