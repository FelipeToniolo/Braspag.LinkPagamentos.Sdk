package com.example.liblinkpagamentos.helper

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {

    private fun getPreferences(context: Context): SharedPreferences? {
        return context.getSharedPreferences("", Context.MODE_PRIVATE)
    }

    operator fun set(context: Context, key: String, value: String) {
        val preferences = getPreferences(context)
        if (preferences != null) {
            val editor = preferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun get(ctx: Context, key: String): String? {
        val preferences = getPreferences(ctx)
        return preferences!!.getString(key, "")
    }
}
