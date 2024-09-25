package com.kamilk2003.rickmortyapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("PropertyName")
data class AppDimens(

    val space0x: Dp = 0.dp,
    val space0_25x: Dp = 2.dp,
    val space0_5x: Dp = 4.dp,
    val space1x: Dp = 8.dp,
    val space1_25x: Dp = 10.dp,
    val space1_5x: Dp = 12.dp,
    val space2x: Dp = 16.dp,
    val space2_5x: Dp = 20.dp,
    val space3x: Dp = 24.dp,
    val space3_5x: Dp = 28.dp,
    val space4x: Dp = 32.dp,
    val space4_5x: Dp = 36.dp,
    val space5x: Dp = 40.dp,
    val space5_5x: Dp = 44.dp,
    val space6x: Dp = 48.dp,
    val space6_5x: Dp = 52.dp,
    val space7x: Dp = 56.dp,
    val space7_5x: Dp = 60.dp,

    val elevation0x: Dp = 0.dp,
    val elevation0_25x: Dp = 1.dp,
    val elevation0_5x: Dp = 2.dp,
    val elevation1x: Dp = 4.dp,
    val elevation2x: Dp = 8.dp,

    val opacity0x: Float = 0f,
    val opacity0_25x: Float = 0.25f,
    val opacity0_5x: Float = 0.5f,
    val opacity0_75x: Float = 0.75f,
    val opacity1x: Float = 1f,

    val cornerRadius0_5x: Dp = 4.dp,
    val cornerRadius1x: Dp = 8.dp,
    val cornerRadius1_5x: Dp = 12.dp,
    val cornerRadius2x: Dp = 16.dp,

    val weight0x: Float = 0f,
    val weight0_1x: Float = 0.1f,
    val weight0_25x: Float = 0.25f,
    val weight0_5x: Float = 0.5f,
    val weight0_75x: Float = 0.75f,
    val weight1x: Float = 1f
)

val LocalAppDimens = staticCompositionLocalOf { AppDimens() }

@Suppress("UnusedReceiverParameter")
val MaterialTheme.dimens: AppDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalAppDimens.current