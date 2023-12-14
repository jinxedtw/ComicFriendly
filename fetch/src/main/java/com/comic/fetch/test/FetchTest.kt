package com.comic.fetch.test

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.comic.fetch.ComicFetchManager
import com.comic.fetch.initializer.FetchInitializer
import org.jsoup.Jsoup
import java.net.URL


fun main() {
//        ComicFetchManager.searchComic("国民校草是女生").collect {
//            println(it)
//        }

    // 腾讯漫画
    //    val doc = Jsoup.connect("https://ac.qq.com/ComicView/index/id/650800/cid/530").userAgent("Mozilla").get()
//    val comicImages = doc.getElementById("comicContain").getElementsByClass("loaded").attr("src")
//    println(doc)

    // 库库漫画
//    val doc2 = Jsoup.connect("http://manhua.ikuku.cc/comiclist/2035/43233/1.htm").get()
//    println(doc2)

    // 包子漫画
//    val doc3 = Jsoup.connect("https://www.dzmanga.com/comic/chapter/diamudiguowuyucongduantoutaikaishidegongzhuzhuanshengnizhuanchuancomic-bingyuewangtobooks/0_24.html").get()
    println("123")

    // 极速漫画
//    val doc4 = Jsoup.connect("https://www.1kkk.com/ch2-1015167-p2/").get()
    println("123")
}

@SuppressLint("SetJavaScriptEnabled")
fun fetchImage() {
    val webView = WebView(FetchInitializer.context)
    webView.settings.javaScriptEnabled = true
    webView.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            webView.evaluateJavascript("javascript:window.HTMLOUT.processHTML(document.documentElement.outerHTML);") { value ->
                // 这里的 value 就是 JavaScript 执行的结果，即 HTML 内容
                val html = value?.replace("\"", "") // 处理 JavaScript 返回的字符串格式
                Log.i(ComicFetchManager.TAG, html ?: "")
            }
        }
    }
    webView.addJavascriptInterface(MyJavaScriptInterface(), "HTMLOUT")
    webView.loadUrl("https://www.1kkk.com/ch2-1015167-p2")
}

class MyJavaScriptInterface {
    @JavascriptInterface
    @SuppressWarnings("unused")
    fun processHTML(html: String) {
        println(html)
        Log.i(ComicFetchManager.TAG, html)
    }
}


