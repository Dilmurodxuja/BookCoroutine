package uz.gita.bookcoroutine.data.remote.apis

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.data.remote.response.AuthResponse.*

interface AuthApi {
    @POST("/auth/sign-up")
    suspend fun signUp(@Body data: AuthRequest.SignUp): Response<SignUp>

    @POST("/auth/sign-up/verify")
    suspend fun signUpVerify(@Header("Authorization")token: String, @Body body: AuthRequest.Verify): Response<Verify>

    @POST("/auth/sign-in")
    suspend fun signIn(@Body data: AuthRequest.SignIn): Response<SignIn>

    @POST("/auth/sign-in/verify")
    suspend fun signInVerify(@Header("Authorization")token: String, @Body body: AuthRequest.Verify): Response<Verify>
}