package com.comic.fetch

import com.comic.fetch.database.entity.Comic
import com.comic.fetch.fetch.FetchHandler
import com.comic.fetch.fetch.TencentFetchHandler
import kotlinx.coroutines.flow.Flow

object ComicFetchManager {

    const val TAG = "comic_fetch"

    /**
     * 真正进行漫画爬取核心操作
     */
    private var fetchHandle: FetchHandler = TencentFetchHandler

    /**
     * 修改漫画源
     */
    fun changeComicSource(@ComicSourceType type: Int) {
        fetchHandle = when (type) {
            TENCENT_SOURCE -> {
                TencentFetchHandler
            }

            else -> {
                TencentFetchHandler
            }
        }
    }

    /**
     * 搜索漫画
     *
     * @param name:漫画名称
     */
    suspend fun searchComic(name: String): Flow<Comic> {
        //腾讯的漫画
        return fetchHandle.searchComic(name)
    }


    /**
     * 获取漫画详情信息
     *
     * @param url:漫画详情页面地址
     */
    suspend fun fetchComicInfo(url: String): Comic {
        return fetchHandle.fetchComicInfo(url)
    }

    /**
     * 获取漫画
     *
     * @param chapterUrl
     */
    suspend fun obtainComicImage(chapterUrl: String): List<String> {
        return fetchHandle.obtainComicImage(chapterUrl)
    }
}