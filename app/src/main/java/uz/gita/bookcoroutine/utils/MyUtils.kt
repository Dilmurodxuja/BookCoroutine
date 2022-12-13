package uz.gita.bookcoroutine.utils

import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback
import retrofit2.Response
import uz.gita.bookcoroutine.data.remote.response.MessageResponse

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun AppCompatEditText.textChangeListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}

fun ContentLoadingProgressBar.state(bool: Boolean) {
    if (bool) this.show()
    else this.hide()
}

fun AppCompatEditText.amount() : String = this.text.toString().trim()

fun <T> Callback<T>.showApiError(response: Response<T>): String{
    val gson = Gson()
    var message = ""
    response.errorBody()?.let {
        try {
            val error = gson.fromJson(it.string(), MessageResponse::class.java)
            message = error.message
        } catch (e: JsonSyntaxException) { return "server o'chirilgan" }
        catch (e: NullPointerException) { return "Ma'lumot kelmadi (NULL)" }
    }
    return message
}

fun <T> Response<*>.showApiError(response: Response<T>): String{
    val gson = Gson()
    var message = ""
    response.errorBody()?.let {
        try {
            val error = gson.fromJson(it.string(), MessageResponse::class.java)
            message = error.message
        } catch (e: JsonSyntaxException) { return "server o'chirilgan" }
        catch (e: NullPointerException) { return "Ma'lumot kelmadi (NULL)" }
    }
    return message
}

fun <T> showApiError(response: Response<T>): String{
    val gson = Gson()
    response.errorBody()?.let {
        try {
            val error = gson.fromJson(it.string(), MessageResponse::class.java)
            return error.message
        } catch (e: JsonSyntaxException) { return "server o'chirilgan" }
        catch (e: NullPointerException) { return "Ma'lumot kelmadi (NULL)" }
    }
    return ""
}

fun AppCompatEditText.clear(){ setText("") }