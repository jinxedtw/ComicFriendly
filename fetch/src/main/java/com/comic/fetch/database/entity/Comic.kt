package com.comic.fetch.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.comic.fetch.ComicSourceType
import java.util.Calendar

@Entity(tableName = "t_point_config")
data class Comic(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    /**
     * 标题
     */
    var title: String = "",

    /**
     * 封面图片
     */
    var cover: String = "",

    /**
     * 作者
     */
    var author: String = "",

    /**
     * 漫画地址
     */
    var comicUrl: String = "",

    /**
     * 章节标题
     */
    var chapters: List<String> = arrayListOf(),

    /**
     * 章节地址
     */
    var chaptersUrl: List<String> = arrayListOf(),

    /**
     * 章节图片
     */
    var comicImage: List<String> = arrayListOf(),

    /**
     * 标签
     */
    var tags: List<String> = arrayListOf(),

    /**
     * 收藏数
     */
    var collections: String = "",

    /**
     * 详情
     */
    var describe: String = "",

    /**
     * 评分
     */
    var point: String = "",

    /**
     * 更新时间
     */
    var updateTime: String = "",

    /**
     * 连载状态
     */
    var serialStatus: String = "",

    /**
     * 上次阅读时间
     */
    var lastReadTime: Calendar = Calendar.getInstance(),

    /**
     * 是否收藏
     */
    var isCollected: Boolean = false,

    /**
     * 当前章节
     */
    var currentChapter: Int = 0,

    /**
     * 当前页
     */
    var currentPage: Int = 0,

    /**
     * 下载完成多少话
     */
    var downloadNum: Int = 0,

    /**
     * 来自什么资源
     */
    @ComicSourceType
    var resourceFrom: Int = 0
)
