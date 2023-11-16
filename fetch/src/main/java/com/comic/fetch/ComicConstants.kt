package com.comic.fetch

import androidx.annotation.IntDef

const val TENCENT_SOURCE = 1

@Retention(AnnotationRetention.SOURCE)
@IntDef(TENCENT_SOURCE)
annotation class ComicSourceType
