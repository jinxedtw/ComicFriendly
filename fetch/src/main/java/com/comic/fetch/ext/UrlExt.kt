package com.comic.fetch.ext

import java.net.URLDecoder
import java.net.URLEncoder


/**
 * url编解码
 */
fun String.decodeUrl(): String {
    return URLDecoder.decode(this, "UTF-8")
}

fun String.encodeUrl(): String? {
    return URLEncoder.encode(this, "UTF-8")
}