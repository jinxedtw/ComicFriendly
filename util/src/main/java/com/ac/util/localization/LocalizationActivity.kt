package com.ac.util.localization

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

open class LocalizationActivity : AppCompatActivity() {
    private var currentLanguage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        setupCurrentLanguage()
        LanguageSetting.onCreate(this)
    }

    /**
     * 设置最近语言
     */
    private fun setupCurrentLanguage() {
        currentLanguage = LanguageSetting.language
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageSetting.attachBaseContext(newBase))
    }

    override fun onResume() {
        try {
            super.onResume()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        LanguageSetting.onResume(this, currentLanguage)

        if (isRtl) {
            onRtlLayout()
        }
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 从右到左布局
     */
    open fun onRtlLayout() {}

    private val isRtl: Boolean
        get() = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL
}