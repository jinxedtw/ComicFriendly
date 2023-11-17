package com.comic.fetch

internal object ComicUrl {

    /**
     * 腾讯搜索链接
     *
     * e.g: https://m.ac.qq.com/search/result?word=%E5%9B%BD%E6%B0%91%E6%A0%A1%E8%8D%89%E6%98%AF%E5%A5%B3%E7%94%9F
     */
    const val TencentSearchUrl = "https://m.ac.qq.com/search/result?word="

    /**
     * 漫画详情页面
     *
     * e.g: https://ac.qq.com/Comic/comicInfo/id/650800
     */
    const val TencentComicInfoUrl = "http://ac.qq.com/Comic/comicInfo/id/"

    /**
     *  漫画具体页面
     *
     *  e.g: https://ac.qq.com/ComicView/index/id/650800/cid/530
     */
    const val TencentComicChapterUrl = "https://ac.qq.com/"
}