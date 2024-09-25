package com.kamilk2003.rickmortyapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kamilk2003.rickmortyapp.ui.theme.appTypo
import com.kamilk2003.rickmortyapp.ui.theme.dimens

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    emptyViewConfig: EmptyViewConfig
) {
    Column(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.space5x)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.dimens.space1x,
            Alignment.CenterVertically
        )
    ) {
        Text(
            text = emptyViewConfig.title.uppercase(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.appTypo.semiboldRoboto1
        )

        Text(
            text = emptyViewConfig.description,
            modifier = Modifier
                .padding(
                    top = MaterialTheme.dimens.space1x,
                    bottom = MaterialTheme.dimens.space3x
                ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.appTypo.normalRoboto2,
            textAlign = TextAlign.Center
        )

        if (emptyViewConfig.onClick != null) {
            TextButton(
                onClick = emptyViewConfig.onClick,
                modifier = modifier
                    .size(
                        height = EmptyViewConstants.buttonHeight,
                        width = EmptyViewConstants.buttonWidth
                    ),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerRadius0_5x),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = emptyViewConfig.buttonTitle.orEmpty(),
                    style = MaterialTheme.appTypo.semiboldRoboto3
                )
            }
        }
    }
}

data class EmptyViewConfig(
    val title: String,
    val description: String,
    val buttonTitle: String? = null,
    val onClick: (() -> Unit)? = null
)

private object EmptyViewConstants {
    val buttonHeight: Dp = 44.dp
    val buttonWidth: Dp = 230.dp
}