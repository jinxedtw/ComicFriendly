package com.comic.fetch.fetch

import com.comic.fetch.database.entity.Comic
import kotlinx.coroutines.flow.Flow

interface FetchHandler {
    suspend fun searchComic(name: String): Flow<Comic>

    suspend fun fetchComicInfo(url: String): Comic

    suspend fun obtainComicImage(chapterUrl: String): Flow<String>
}