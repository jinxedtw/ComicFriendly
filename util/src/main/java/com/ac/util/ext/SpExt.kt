package com.ac.util.ext

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun booleanSP(key: String, defaultValue: Boolean = false): ReadWriteProperty<Any, Boolean> =
    SPUtil.defaultPref().delegate(key, defaultValue, SharedPreferences::getBoolean, Editor::putBoolean)

fun intSP(key: String, defaultValue: Int = 0): ReadWriteProperty<Any, Int> =
    SPUtil.defaultPref().delegate(key, defaultValue, SharedPreferences::getInt, Editor::putInt)

fun longSP(key: String, defaultValue: Long = 0L): ReadWriteProperty<Any, Long> =
    SPUtil.defaultPref().delegate(key, defaultValue, SharedPreferences::getLong, Editor::putLong)

fun floatSP(key: String, defaultValue: Float = 0.0F): ReadWriteProperty<Any, Float> =
    SPUtil.defaultPref().delegate(key, defaultValue, SharedPreferences::getFloat, Editor::putFloat)

fun stringSP(key: String, defaultValue: String = ""): ReadWriteProperty<Any, String> =
    SPUtil.defaultPref().nullAbleDelegate(key, defaultValue, SharedPreferences::getString, Editor::putString)

fun stringSetSP(key: String, defaultValue: Set<String> = emptySet()): ReadWriteProperty<Any, Set<String>?> =
    SPUtil.defaultPref().nullAbleDelegate(key, defaultValue, SharedPreferences::getStringSet, Editor::putStringSet)

private inline fun <T> SharedPreferences.delegate(
    key: String, defaultValue: T, crossinline getter: SharedPreferences.(String, T) -> T, crossinline setter: Editor.(String, T) -> Editor
): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter(key, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        edit().setter(key, value).apply()
    }
}

private inline fun <T> SharedPreferences.nullAbleDelegate(
    key: String, defaultValue: T, crossinline getter: SharedPreferences.(String, T?) -> T?, crossinline setter: Editor.(String, T?) -> Editor
): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter(key, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        edit().setter(key, value).apply()
    }
}

object SPUtil {
    private lateinit var sharedPreferences: SharedPreferences

    fun initSp(context: Context) {
        sharedPreferences = context.getSharedPreferences("app_sp", Context.MODE_PRIVATE)
    }

    fun defaultPref(): SharedPreferences {
        return sharedPreferences
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: ""
    }

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}