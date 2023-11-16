package com.comic.app.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.comic.app.R
import com.comic.app.ui.theme.AppTheme
import com.comic.fetch.ComicFetchManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LearnBaseWidget()
                }
            }
        }
    }
}

@Composable
fun LearnModifier() {
    Image(
        modifier = Modifier
            .wrapContentSize(align = Alignment.CenterStart)
            .border(5.dp, Color.Blue, CircleShape)
            .clip(CircleShape)
            .rotate(180f),
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Icon Image"
    )
}

@Composable
fun LearnBaseWidget() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
        AsyncImage(model = "https://img-blog.csdnimg.cn/20200401094829557.jpg", contentDescription = "First line of code")
        Text(text = "郑州", color = MaterialTheme.colorScheme.primary, fontSize = 26.sp)
        Text(modifier = Modifier.align(Alignment.Start), text = "July 2021")
        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
//                    ComicFetchManager.searchComic("王者").collect{
//                        Log.i(ComicFetchManager.TAG,it.toString())
//                    }

                    val comic = ComicFetchManager.fetchComicInfo("https://ac.qq.com/Comic/comicInfo/id/650800")
                    Log.i(ComicFetchManager.TAG, comic.toString())
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "This is Button",
                color = Color.White,
                fontSize = 26.sp
            )
        }
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Type something here")
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            )
        )
        CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 6.dp
        )
        AsyncImage(model = "https://img-blog.csdnimg.cn/20200401094829557.jpg", contentDescription = "First line of code")
    }
}
