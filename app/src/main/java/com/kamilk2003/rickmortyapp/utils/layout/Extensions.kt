package com.kamilk2003.rickmortyapp.utils.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun mixedPaddingValues(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp
): PaddingValues {
    return PaddingValues(
        start = if (horizontal != 0.dp) horizontal else start,
        top = if (vertical != 0.dp) vertical else top,
        end = if (horizontal != 0.dp) horizontal else end,
        bottom = if (vertical != 0.dp) vertical else bottom
    )
}