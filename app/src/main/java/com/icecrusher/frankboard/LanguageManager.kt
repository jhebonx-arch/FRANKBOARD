package com.icecrusher.frankboard

import android.content.Context

object LanguageManager {

    private const val PREFS_NAME = "frankboard_settings"
    private const val LANGUAGE_KEY = "language"

    fun getLanguage(context: Context): AppLanguage {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        return when (
            preferences.getString(
                LANGUAGE_KEY,
                AppLanguage.RUSSIAN.name
            )
        ) {
            AppLanguage.ENGLISH.name ->
                AppLanguage.ENGLISH

            else ->
                AppLanguage.RUSSIAN
        }
    }

    fun saveLanguage(
        context: Context,
        language: AppLanguage
    ) {

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                LANGUAGE_KEY,
                language.name
            )
            .apply()
    }
}