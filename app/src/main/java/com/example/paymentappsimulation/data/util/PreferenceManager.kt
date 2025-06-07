package com.example.paymentappsimulation.data.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "user_info"
    private const val KEY_MERCHANT = "merchant_name"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUsername(context: Context, username: String) {
        getPrefs(context).edit().putString(KEY_MERCHANT, username).apply()
    }

    fun getUsername(context: Context): String? {
        return getPrefs(context).getString(KEY_MERCHANT, null)
    }

    fun clearUsername(context: Context) {
        getPrefs(context).edit().remove(KEY_MERCHANT).apply()
    }
}
