package uz.gita.bookcoroutine.data.remote.response

import com.google.gson.annotations.SerializedName

sealed class AuthResponse {
    data class SignUp(
        @SerializedName("token")
        val token: String
    )

    data class SignIn(
        @SerializedName("token")
        val token: String
    )

    data class Verify(
        @SerializedName("token")
        val accessToken: String
    )
}