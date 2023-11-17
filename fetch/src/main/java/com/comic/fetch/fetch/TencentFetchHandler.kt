package com.comic.fetch.fetch

import android.util.Log
import com.comic.fetch.ComicFetchManager
import com.comic.fetch.ComicUrl
import com.comic.fetch.TENCENT_SOURCE
import com.comic.fetch.database.entity.Comic
import com.comic.fetch.ext.encodeUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.text.DecimalFormat

internal object TencentFetchHandler : FetchHandler {
    const val TAG = ComicFetchManager.TAG + "_tencent"

    override suspend fun searchComic(name: String): Flow<Comic> {
        return flow {
            Log.i(TAG, "url: ${ComicUrl.TencentSearchUrl + name.encodeUrl()}")
            val doc = Jsoup.connect(ComicUrl.TencentSearchUrl + name.encodeUrl()).get()
            Log.i(TAG, "html: ${doc.text()}")

            val detail: List<Element> = doc.getElementsByAttributeValue("class", "comic-item")
            for (i in detail.indices) {
                val info: List<Element> = detail[i].select("small")
                val fetchTags: MutableList<String> = ArrayList()
                val descriptions = info[1].text().split("：".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (j in descriptions.indices) {
                    fetchTags.add(descriptions[j])
                }

                val comic = Comic().apply {
                    title = detail[i].select("strong").text()
                    cover = detail[i].select("img").attr("src")
                    comicUrl = ComicUrl.TencentComicInfoUrl + getComicId(detail[i].select("a").attr("href"))
                    resourceFrom = TENCENT_SOURCE
                    updateTime = info[0].text()
                    author = info[2].text()
                    tags = fetchTags
                }
                emit(comic)
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchComicInfo(url: String): Comic {
        val doc = Jsoup.connect(url).get()
        // 设置标签
        val descriptions = doc.getElementsByAttributeValue("name", "Description")[0].select("meta").attr("content")
        val descriptionInfos = descriptions.split("：".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val comicTags: MutableList<String> = arrayListOf()
        descriptionInfos[descriptionInfos.size - 1].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().forEach {
            comicTags.add(it)
        }
        val detail = doc.getElementsByAttributeValue("class", "works-cover ui-left")[0]
        val comicAuthor = doc.getElementsByAttributeValue("class", " works-author-name")[0]
        // 设置收藏数
        val collect = doc.getElementsByAttributeValue("id", "coll_count")[0]
        val decimalFormat = DecimalFormat(".00") //构造方法的字符格式这里如果小数不足2位,会以0补足.
        val collection = decimalFormat.format((collect.text().toFloat() / 10000).toDouble()) //format 返回的是字符串
        // 设置章节数
        val divChapter = doc.getElementsByAttributeValue("class", "chapter-page-all works-chapter-list")[0]
        val elementChapters: List<Element> = divChapter.getElementsByAttributeValue("target", "_blank")
        val comicChapterUrls: MutableList<String> = arrayListOf()
        val comicChapters: MutableList<String> = arrayListOf()
        elementChapters.forEach {
            comicChapters.add(it.select("a").text())
            comicChapterUrls.add("${ComicUrl.TencentComicChapterUrl}${it.select("a").attr("href")}")
        }
        val elementDescribe = doc.getElementsByAttributeValue("class", "works-intro-short ui-text-gray9")[0]
        // 设置状态
        val comicStatus = detail.select("label")[0].text()
        val elementUpdate = doc.getElementsByAttributeValue("class", " ui-pl10 ui-text-gray6")[0]
        val updates = elementUpdate.select("span")[0].text()
        // 设置评分
        val elementPoint = doc.getElementsByAttributeValue("class", "ui-text-orange")[0]

        return Comic().apply {
            comicUrl = url
            title = doc.title().split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            tags = comicTags
            cover = detail.select("img").attr("src")
            author = comicAuthor.select("a").attr("title")
            collections = "($collection)万"
            chapters = comicChapters
            chaptersUrl = comicChapterUrls
            describe = elementDescribe.select("p").text()
            serialStatus = if (comicStatus == "已完结") {
                comicStatus
            } else {
                "更新最新话"
            }
            updateTime = if (comicStatus == "已完结") {
                "全" + elementChapters.size + "话"
            } else {
                updates
            }
            point = elementPoint.select("strong")[0].text()
        }
    }

    override suspend fun obtainComicImage(chapterUrl: String): Flow<String> {
        return flow<String> {

        }.flowOn(Dispatchers.IO)
    }


    /**
     * 获取漫画ID
     */
    private fun getComicId(splitID: String): String {
        val ids = splitID.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return ids[ids.size - 1]
    }
}