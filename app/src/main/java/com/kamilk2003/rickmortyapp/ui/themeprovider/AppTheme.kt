package com.kamilk2003.rickmortyapp.ui.themeprovider

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.kamilk2003.rickmortyapp.ui.theme.AppDimens
import com.kamilk2003.rickmortyapp.ui.theme.AppTypography
import com.kamilk2003.rickmortyapp.ui.theme.LocalAppDimens
import com.kamilk2003.rickmortyapp.ui.theme.LocalAppTypography
import com.kamilk2003.rickmortyapp.ui.theme.darkScheme
import com.kamilk2003.rickmortyapp.ui.theme.lightScheme

@Composable
fun RickMortyAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) lightScheme
        else darkScheme

    MaterialTheme(colorScheme = colors) {
        CompositionLocalProvider(
            LocalAppTypography provides AppTypography,
            LocalAppDimens provides AppDimens(),
            content = content
        )
    }
}