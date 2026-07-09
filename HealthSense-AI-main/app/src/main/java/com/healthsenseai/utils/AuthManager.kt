package com.healthsenseai.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import java.security.MessageDigest

object AuthManager {
    private const val PREFS = "auth_prefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASS_HASH = "pass_hash"
    private const val KEY_LOGGED_IN = "logged_in"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun isLoggedIn(context: Context): Boolean =
        prefs(context).getBoolean(KEY_LOGGED_IN, false)

    fun logout(context: Context) {
        prefs(context).edit().putBoolean(KEY_LOGGED_IN, false).apply()
    }

    fun validateEmail(email: String): Boolean =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun validatePassword(password: String): Boolean =
        password.length >= 6

    fun signup(context: Context, email: String, password: String): Boolean {
        if (!validateEmail(email) || !validatePassword(password)) return false
        val p = prefs(context)
        p.edit()
            .putString(KEY_EMAIL, email.trim())
            .putString(KEY_PASS_HASH, hash(password))
            .putBoolean(KEY_LOGGED_IN, true)
            .apply()
        return true
    }

    fun login(context: Context, email: String, password: String): Boolean {
        val p = prefs(context)
        val storedEmail = p.getString(KEY_EMAIL, null)
        val storedHash = p.getString(KEY_PASS_HASH, null)
        val ok = storedEmail != null &&
                storedHash != null &&
                storedEmail.equals(email.trim(), ignoreCase = true) &&
                storedHash == hash(password)
        if (ok) {
            p.edit().putBoolean(KEY_LOGGED_IN, true).apply()
        }
        return ok
    }

    private fun hash(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}