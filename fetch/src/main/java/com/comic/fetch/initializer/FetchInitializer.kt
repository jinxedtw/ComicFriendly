package com.comic.fetch.initializer

import android.annotation.SuppressLint
import android.content.Context
import androidx.startup.Initializer

class FetchInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        FetchInitializer.context = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}