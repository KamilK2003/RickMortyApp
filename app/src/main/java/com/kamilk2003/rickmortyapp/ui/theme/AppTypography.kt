package com.kamilk2003.rickmortyapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {

    val normalRoboto2: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal
    )

    val semiboldRoboto1: TextStyle = TextStyle(
        fontSize = 21.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold
    )
    val semiboldRoboto2: TextStyle = TextStyle(
        fontSize = 17.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold
    )
    val semiboldRoboto3: TextStyle = TextStyle(
        fontSize = 15.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold
    )

    val boldRoboto2: TextStyle = TextStyle(
        fontSize = 23.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold
    )
}

val LocalAppTypography = staticCompositionLocalOf { AppTypography }

@Suppress("UnusedReceiverParameter")
val MaterialTheme.appTypo: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current