package com.comic.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.ac.util.ext.openLog

class ComicFriendlyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        initMain()
        initOther()
    }

    private fun initMain() {

    }

    private fun initOther() {
        val appObserver = ApplicationObserver()
        registerActivityLifecycleCallbacks(appObserver)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appObserver)

        openLog(true)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}