package uz.gita.bookcoroutine.domain.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import uz.gita.bookcoroutine.data.local.LocalStorage
import uz.gita.bookcoroutine.data.remote.ApiClient
import uz.gita.bookcoroutine.data.remote.apis.AuthApi
import uz.gita.bookcoroutine.data.remote.request.AuthRequest
import uz.gita.bookcoroutine.data.remote.response.MessageResponse

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val localStorage: LocalStorage,
    private val gson: Gson
) : AuthRepository {
    companion object {
        private var repository: AuthRepository? = null

        fun getInstance() = if (repository != null) repository!! else {
            val api = ApiClient.retrofit.create(AuthApi::class.java)
            val localStorage = LocalStorage.getInstance()
            val temp = AuthRepositoryImpl(api, localStorage, Gson())
            repository = temp
            temp
        }
    }

    private val fullToken
        get() = "Bearer ${localStorage.token}"

    override fun signUp(data: AuthRequest.SignUp) = flow<Result<Unit>> {
        val response = authApi.signUp(data)
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.token = it.token
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun signUpVerify(body: AuthRequest.Verify) = flow<Result<Unit>> {
        val response = authApi.signUpVerify(fullToken, body)
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.token = it.accessToken
                localStorage.startScreen = "dashboard"
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun signIn(data: AuthRequest.SignIn) = flow<Result<Unit>> {
        Log.d("TTT", "signIn: sign")
        val response = authApi.signIn(data)
        Log.d("TTT", "signIn: in")
        if (response.isSuccessful) {
            Log.d("TTT", "response: success")
            response.body()?.let {
                localStorage.token = it.token
                Log.d("TTT", "response: has body")
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun signInVerify(body: AuthRequest.Verify) = flow<Result<Unit>> {
        val response = authApi.signInVerify(fullToken, body)
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.token = it.accessToken
                localStorage.startScreen = "dashboard"
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    private fun gsonConverter(response: Response<*>): String {
        if (response.errorBody() == null) return ""
        val errorResponse = gson.fromJson(response.errorBody()!!.string(), MessageResponse::class.java)
        return errorResponse.message
    }
}