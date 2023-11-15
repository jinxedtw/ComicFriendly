package com.ac.util.ext

import android.util.Log

const val VERBOSE = 1
const val DEBUG = 2
const val INFO = 3
const val WARN = 4
const val ERROR = 5
const val CLOSE = 6


private var level = CLOSE

fun setLogLevel(sLevel: Int) {
    level = sLevel
}

fun openLog(open: Boolean) {
    level = if (open) {
        VERBOSE
    } else {
        CLOSE
    }
}

fun logV(tag: String, msg: String?) {
    if (level <= VERBOSE) {
        Log.v(tag, msg.toString())
    }
}

fun logD(tag: String, msg: String?) {
    if (level <= DEBUG) {
        Log.d(tag, msg.toString())
    }
}

fun logI(tag: String, msg: String?) {
    if (level <= INFO) {
        Log.i(tag, msg.toString())
    }
}

fun logW(tag: String, msg: String?, tr: Throwable? = null) {
    if (level <= WARN) {
        if (tr == null) {
            Log.w(tag, msg.toString())
        } else {
            Log.w(tag, msg.toString(), tr)
        }
    }
}

fun logE(tag: String, msg: String?, tr: Throwable) {
    if (level <= ERROR) {
        Log.e(tag, msg.toString(), tr)
    }
}