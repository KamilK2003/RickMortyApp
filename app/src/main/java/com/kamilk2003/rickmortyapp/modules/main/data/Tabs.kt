package com.kamilk2003.rickmortyapp.modules.main.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kamilk2003.rickmortyapp.R

enum class Tabs(
    @StringRes val category: Int,
    @DrawableRes val icon: Int
) {
    ALL(
        category = R.string.app_main_screen_category_all_title,
        icon = R.drawable.characters_icon
    ),
    FAVOURITE(
        category = R.string.app_main_screen_category_favourite_title,
        icon = R.drawable.favourite_characters_icon
    )
}