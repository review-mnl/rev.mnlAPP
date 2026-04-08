package com.example.reviewmnl.data.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Simple singleton for persisting the JWT auth token across app sessions.
 * Call [init] once from MainActivity before using [token].
 */
object TokenManager {
    private const val PREFS_NAME = "reviewmnl_prefs"
    private const val KEY_TOKEN = "auth_token"

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        prefs = context.applicationContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var token: String?
        get() = prefs?.getString(KEY_TOKEN, null)
        set(value) {
            prefs?.edit()?.apply {
                if (value != null) putString(KEY_TOKEN, value) else remove(KEY_TOKEN)
                apply()
            }
        }

    fun clear() {
        token = null
    }
}
