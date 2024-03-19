package com.demo.retrofitroom

import android.content.Context
import android.content.SharedPreferences

object SP {

    private const val FILE_NAME = "rotroom_sp"
    private const val KEY_USERNAME = "username"
    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    var username: String
        get() {
            return sp.getString(KEY_USERNAME, "square") ?: "square"
        }
        set(value) {
            sp.edit().putString(KEY_USERNAME, value).apply()
        }

}
