package com.ac.util.initializer

import android.annotation.SuppressLint
import android.content.Context
import androidx.startup.Initializer

class HelpInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        initializerContext = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var initializerContext: Context
    }
}