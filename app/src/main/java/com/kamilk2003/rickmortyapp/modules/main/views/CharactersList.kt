package com.kamilk2003.rickmortyapp.modules.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kamilk2003.rickmortyapp.ui.theme.appTypo
import com.kamilk2003.rickmortyapp.ui.theme.dimens
import com.kamilk2003.rickmortyapp.views.EmptyView
import com.kamilk2003.rickmortyapp.views.EmptyViewConfig

@Composable
fun CharactersList( // TODO("Change data types in constructor after VM integration")
    characters: List<String>,
    isFavouriteCharacter: (String) -> Boolean,
    emptyViewConfig: EmptyViewConfig,
    onCharacterClick: () -> Unit
) {

    // MARK: - Stored Properties

    val lazyGridState = rememberLazyStaggeredGridState()

    // MARK: - View

    if (characters.isEmpty()) {
        EmptyView(emptyViewConfig = emptyViewConfig)
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(CharactersListConstants.columnsCount),
            modifier = Modifier.fillMaxSize(),
            state = lazyGridState,
            contentPadding = PaddingValues(
                top = MaterialTheme.dimens.space2x,
                bottom = CharactersListConstants.contentBottomPadding
            ),
            verticalItemSpacing = MaterialTheme.dimens.space2x,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space2x)
        ) {
            items(characters) { character ->
                Card(
                    modifier = Modifier
                        .height(CharactersListConstants.cardHeight),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerRadius1x),
                    elevation = CardDefaults.cardElevation(MaterialTheme.dimens.elevation0_5x),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(MaterialTheme.dimens.space1x)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://rickandmortyapi.com/api/character/avatar/1.jpeg") // TODO("Replace with proper implementation")
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(CharactersListConstants.imageSize)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally),
                            placeholder = null, // TODO("Add placeholder in case of slow loading")
                            contentScale = ContentScale.Crop
                        )

                        ResponsiveText(
                            text = character,
                            modifier = Modifier
                                .padding(top = MaterialTheme.dimens.space1x)
                                .weight(MaterialTheme.dimens.weight1x)
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            textStyle = MaterialTheme.appTypo.semiboldRoboto2,
                            maxLines = MaterialTheme.dimens.maxLines2x
                        )

                        Card(
                            onClick = onCharacterClick,
                            modifier = Modifier
                                .height(CharactersListConstants.buttonHeight)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(MaterialTheme.dimens.cornerRadius0_5x),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isFavouriteCharacter(character)) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(CharactersListConstants.iconSize),
                                    tint = if (isFavouriteCharacter(character)) MaterialTheme.colorScheme.secondaryContainer
                                    else MaterialTheme.colorScheme.primaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private object CharactersListConstants {
    const val columnsCount: Int = 2
    val cardHeight: Dp = 210.dp
    val imageSize: Dp = 100.dp
    val buttonHeight: Dp = 35.dp
    val iconSize: Dp = 24.dp
    val contentBottomPadding: Dp = 90.dp
}