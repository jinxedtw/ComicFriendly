package com.ac.autoclick.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ImageTextItem(
    @StringRes val text: Int,
    @DrawableRes val image: Int,
)