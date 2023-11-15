package com.ac.util.ext

import android.text.SpannableStringBuilder
import android.text.Spanned

/**
 *   val spannableString = SpannableStringBuilder()
 *         spannableString.appendSpans("I understand the ", AbsoluteSizeSpan(14, true))
 *         spannableString.appendSpans(
 *             "Privacy Policy",
 *             AbsoluteSizeSpan(14, true),
 *             object : ClickableSpan() {
 *                 override fun onClick(widget: View) {
 *                     openWebpage(BuildConfig.PRIVACY_URL)
 *                 }
 *
 *                 override fun updateDrawState(ds: TextPaint) {
 *                     ds.color = Color.parseColor("#FF1860")
 *                     ds.isUnderlineText = true
 *                 }
 *             })
 *
 *             val spannableString = SpannableStringBuilder()
 *         spannableString.appendSpans(context.getString(R.string.popup_explain), ForegroundColorSpan(0xFF333333.toInt()))
 *         spannableString.appendSpans(
 *             "https://www.nia.nihgov/health/high-blood-pressure-and-older-adults for more information (NIH)",
 *             ForegroundColorSpan(0xFF9C68FF.toInt())
 *         )
 */
fun SpannableStringBuilder.appendSpans(text: CharSequence, vararg whatArray: Any?, flags: Int = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) {
    var index = -1
    for (what in whatArray) {
        if (index != -1) {
            setSpans(text, what, flags = flags)
        } else {
            if (what == null) {
                append(text)
            } else {
                append(text, what, flags)
            }
            index = lastIndexOf(text.toString())
        }
    }
}

fun SpannableStringBuilder.setSpans(text: CharSequence, vararg whatArray: Any?, flags: Int = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) {
    val start = toString().lastIndexOf(text.toString())
    val end = start + text.length
    if (start != -1 && start < end && start <= length && end <= length) {
        for (what in whatArray) {
            setSpan(what, start, end, flags)
        }
    }
}
