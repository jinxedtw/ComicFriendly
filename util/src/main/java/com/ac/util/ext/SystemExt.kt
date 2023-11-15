package com.ac.util.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast


fun Context.openUrl(url: String) {
    var mUrl: String = url
    if (url.startsWith("www.")) {
        mUrl = "http:${url}"
    }

    openIntent(action = Intent.ACTION_VIEW, uri = Uri.parse(mUrl))
}

fun Context.openEmail(emailAddress: String) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:") // 使用"mailto:"作为数据URI

    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
    intent.putExtra(Intent.EXTRA_SUBJECT, "")
    intent.putExtra(Intent.EXTRA_TEXT, "")

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, "Error intent", Toast.LENGTH_SHORT).show()
    }
}

fun Context.shareText(text: String?) {
    if (text == null) {
        return
    }
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_TEXT, text)
    intent.type = "text/plain"

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(intent, "share"))
    } else {
        Toast.makeText(this, "Error intent", Toast.LENGTH_SHORT).show()
    }
}

fun Context.copyText(data: String?) {
    if (data == null) {
        return
    }
    (this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
        setPrimaryClip(ClipData.newPlainText("copy Content", data))
    }
    Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
}

fun Context.openIntent(action: String? = null, type: String? = null, uri: Uri? = null) {
    Intent()
        .apply {
            action?.let { this.action = it }
            uri?.let { this.data = it }
            type?.let { this.type = it }
        }
        .let { intent ->
            packageManager?.let {
                if (intent.resolveActivity(it) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error intent", Toast.LENGTH_SHORT).show()
                }
            }
        }
}