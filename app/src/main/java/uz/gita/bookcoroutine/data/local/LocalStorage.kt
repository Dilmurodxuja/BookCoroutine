package uz.gita.bookcoroutine.data.local

import android.content.Context
import uz.gita.bookcoroutine.app.App
import uz.gita.bookcoroutine.utils.SharedPreference

class LocalStorage private constructor(context: Context) : SharedPreference(context) {
    companion object {
        @Volatile
        private lateinit var instance: LocalStorage

        fun getInstance(): LocalStorage {
            if (!::instance.isInitialized) instance = LocalStorage(App.instance)
            return instance
        }
    }

    var first: Boolean by booleans(true)

    var startScreen: String by strings("login")
    var token: String by strings()
    var refreshToken: String by strings()
    var accessToken: String by strings()
}