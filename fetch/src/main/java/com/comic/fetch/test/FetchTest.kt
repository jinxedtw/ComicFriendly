package com.comic.fetch.test

import android.util.Log
import com.comic.fetch.ComicFetchManager
import com.comic.fetch.fetch.TencentFetchHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch {
        ComicFetchManager.searchComic("国民校草是女生").collect {
            println(it)
        }
    }
}