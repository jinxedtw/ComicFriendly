package com.ac.util.ext

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider


inline fun setOnClickListeners(vararg v: View?, crossinline block: View.() -> Unit) {
    val listener = View.OnClickListener {
        it.block()
    }
    v.forEach {
        it?.setOnClickListener(listener)
    }
}

inline fun setOnLongClickListeners(vararg v: View?, crossinline block: View.() -> Boolean) {
    val listener = View.OnLongClickListener {
        it.block()
    }
    v.forEach {
        it?.setOnLongClickListener(listener)
    }
}


private const val LAST_CLICK_TIME = -0x7bc7dbad

private const val DEFAULT_CLICK_DURATION = 500


fun View.isFastClick(duration: Long): Boolean {
    val currentDuration = if (duration == 0L) DEFAULT_CLICK_DURATION.toLong() else duration
    val currentTimeMillis = System.currentTimeMillis()
    val lastClickTime = if (getTag(LAST_CLICK_TIME) != null) getTag(LAST_CLICK_TIME) as Long else 0L
    if (lastClickTime > currentTimeMillis) {
        setTag(LAST_CLICK_TIME, 0L)
        return false
    }
    if (currentTimeMillis - lastClickTime < currentDuration) {
        return true
    }
    setTag(LAST_CLICK_TIME, currentTimeMillis)
    return false
}


fun View.isFastClick(): Boolean {
    return isFastClick(0L)
}

class RoundedViewOutlineProvider(private val radius: Int = 0) : ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        val width = view?.width ?: 0
        val height = view?.height ?: 0
        outline?.setRoundRect(0, 0, width, height, radius.toFloat())
    }
}

class CircleViewOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        val width = view?.width ?: 0
        val height = view?.height ?: 0
        val diameter = width.coerceAtMost(height)

        outline?.setOval(0, 0, diameter, diameter)
    }
}

fun View.setRounderConner(radius: Int = 0) {
    clipToOutline = true
    outlineProvider = RoundedViewOutlineProvider(radius)
}

fun View.setCircle() {
    clipToOutline = true
    outlineProvider = CircleViewOutlineProvider()
}