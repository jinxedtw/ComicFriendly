package com.ac.util.localization

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.LocaleList
import android.text.TextUtils
import com.ac.util.UtilConstants
import com.ac.util.initializer.HelpInitializer
import java.util.Locale

object LanguageSetting {

    var language: String
        /**
         * 获取默认语言
         */
        get() = getSharedPreferences().getString(UtilConstants.LOCALIZATION_SAVE_LANGUAGE, getDefault()) ?: getDefault()
        set(language) {
            getSharedPreferences().edit().putString(UtilConstants.LOCALIZATION_SAVE_LANGUAGE, language).apply()
        }

    private fun getSharedPreferences(): SharedPreferences {
        return HelpInitializer.initializerContext.getSharedPreferences(UtilConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    /**
     * return app language base context
     */
    fun attachBaseContext(newBase: Context): Context {
        //设置app语言的地方
        val newLanguage = language
        var newLocale: Locale? = Locale(newLanguage)
        if (LocalizationConstants.LANGUAGE_RTW == newLanguage) {
            newLocale = Locale.TAIWAN
        }
        if (LocalizationConstants.LANGUAGE_RCN == newLanguage) {
            newLocale = Locale.CHINA
        }
        val resources = newBase.resources
        val conf = resources.configuration
        conf.setLocale(newLocale)
        conf.setLayoutDirection(conf.locale)
        if (newLocale != null) {
            Locale.setDefault(newLocale)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(newLocale)
            LocaleList.setDefault(localeList)
        }
        return newBase.createConfigurationContext(conf)
    }

    fun onCreate(activity: Activity) {
        val config = activity.resources.configuration
        val direction = config.layoutDirection
        val currentDirection = activity.window.decorView.layoutDirection
        if (direction != currentDirection) {
            activity.window.decorView.layoutDirection = direction
        }
    }

    fun onResume(activity: Activity, language: String?) {
        if (language == null) {
            return
        }

        if (!isSameLanguage(language)) {
            Handler().post { activity.recreate() }
        }
    }

    private fun getLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else context.resources.configuration.locale
    }

    private fun getDefault(): String {
        val language: String
        val locale = getLocale(HelpInitializer.initializerContext)
        language = locale.language

        if (language == "zh") {
            val lang: String = locale.country + "_" + locale.language
            return if (lang == "TW_zh") {
                LocalizationConstants.LANGUAGE_RTW
            } else {
                LocalizationConstants.LANGUAGE_RCN
            }
        }
        return language
    }

    private fun isSameLanguage(current: String): Boolean {
        val language = language
        return TextUtils.equals(current, language)
    }

    fun resetLanguage(activity: Activity, lan: String, intent: Intent) {
        if (!isSameLanguage(lan)) {
            language = lan
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }
}