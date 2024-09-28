package com.kamilk2003.rickmortyapp.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.kamilk2003.rickmortyapp.ui.theme.dimens

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun ResponsiveText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = TextAlign.Start,
    textStyle: TextStyle,
    maxLines: Int = MaterialTheme.dimens.maxLines1x,
    lineHeight: TextUnit? = null
) {

    // MARK: - Stored Properties

    var textSize by remember { mutableStateOf(textStyle.fontSize) }

    // MARK: - View

    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = textSize,
        style = textStyle,
        maxLines = maxLines,
        lineHeight = lineHeight ?: TextUnit.Unspecified,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                textSize = textSize.times(TEXT_SCALE_REDUCTION_INTERVAL)
            }
        }
    )
}