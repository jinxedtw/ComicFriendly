package com.ac.util.ext

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log

fun Context.openActivity(cls: Class<*>) {
    runCatching {
        startActivity(Intent(this, cls))
    }
}

inline fun <T, R> T.runTimeLog(tag: String? = null, block: T.() -> R): R {
    val startTime = System.currentTimeMillis()
    val result = block()
    Log.d("$tag@runtime", "${System.currentTimeMillis() - startTime}ms")
    return result
}

inline fun <R> runTimeLog(tag: String? = null, block: () -> R): R {
    val startTime = System.currentTimeMillis()
    val result = block()
    Log.d("$tag@runtime", "${System.currentTimeMillis() - startTime}ms")
    return result
}

fun runDelay(time: Long, runnable: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(runnable, time)
}