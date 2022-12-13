package uz.gita.bookcoroutine.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bookcoroutine.data.remote.request.AuthRequest

interface AuthRepository {
    fun signUp(data: AuthRequest.SignUp): Flow<Result<Unit>>

    fun signUpVerify(body: AuthRequest.Verify): Flow<Result<Unit>>

    fun signIn(data: AuthRequest.SignIn): Flow<Result<Unit>>

    fun signInVerify(body: AuthRequest.Verify): Flow<Result<Unit>>
}