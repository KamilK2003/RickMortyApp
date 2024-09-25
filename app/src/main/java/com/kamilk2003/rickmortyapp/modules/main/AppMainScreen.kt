package com.kamilk2003.rickmortyapp.modules.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kamilk2003.rickmortyapp.R
import com.kamilk2003.rickmortyapp.modules.main.data.Tabs
import com.kamilk2003.rickmortyapp.modules.main.views.CharactersList
import com.kamilk2003.rickmortyapp.modules.main.views.ResponsiveText
import com.kamilk2003.rickmortyapp.ui.theme.appTypo
import com.kamilk2003.rickmortyapp.ui.theme.dimens
import com.kamilk2003.rickmortyapp.views.EmptyViewConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMainScreen() { // TODO("Add snackbars after VM integration")

    // MARK: - Stored Properties

    val charactersList = listOf("Rick", "Morty", "Robin", "Mark", "Mick", "Kick", "Vick", "Sick") // TODO("Replace after VM integration")
    val favouriteCharacters = listOf("Rick", "Mark", "Evans") // TODO("Replace after VM integration")

    var selectedTab by rememberSaveable { mutableStateOf(Tabs.ALL) }

    val pageState = rememberPagerState(initialPage = selectedTab.ordinal) { 2 }

    var isRefreshing by remember { mutableStateOf(false) } // TODO("Replace after VM integration")
    val coroutine = rememberCoroutineScope() // TODO("Remove after VM integration")

    // MARK: - Launched Effect

    LaunchedEffect(selectedTab) {
        pageState.animateScrollToPage(selectedTab.ordinal)
    }

    // MARK: - View

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    ResponsiveText(
                        text = stringResource(id = R.string.app_main_screen_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        textStyle = MaterialTheme.appTypo.boldRoboto2
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTab.ordinal
            ) {
                Tabs.entries.forEach { tab ->
                    Tab(
                        modifier = Modifier
                            .fillMaxWidth(
                                if (tab.ordinal == 0) MaterialTheme.dimens.weight0_5x
                                else MaterialTheme.dimens.weight1x
                            ),
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                text = stringResource(id = tab.category),
                                style = MaterialTheme.appTypo.semiboldRoboto3
                            )
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.icon),
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.dimens.space2x),
                isRefreshing = isRefreshing,
                onRefresh = {
                    // TODO("Change to proper implementation")
                    isRefreshing = true
                    coroutine.launch {
                        delay(2000) // INFO("Imitation of API call")
                        isRefreshing = false
                    }
                },
                contentAlignment = Alignment.Center
            ) {
                when (selectedTab) {
                    Tabs.ALL -> {
                        CharactersList(
                            characters = charactersList,
                            isFavouriteCharacter = {
                                false // TODO("Change implementation")
                            },
                            emptyViewConfig = EmptyViewConfig(
                                title = stringResource(id = R.string.app_main_screen_empty_characters_title),
                                description = stringResource(id = R.string.app_main_screen_empty_characters_description),
                                buttonTitle = stringResource(id = R.string.app_main_screen_empty_characters_button_title),
                                onClick = {
                                    // TODO("Change to proper implementation")
                                    isRefreshing = true
                                    coroutine.launch {
                                        delay(2000) // INFO("Imitation of API call")
                                        isRefreshing = false
                                    }
                                }
                            ),
                            onCharacterClick = {
                                // TODO("Add implementation")
                            }
                        )
                    }
                    Tabs.FAVOURITE -> {
                        CharactersList(
                            characters = favouriteCharacters,
                            isFavouriteCharacter = {
                                true // TODO("Change implementation")
                            },
                            emptyViewConfig = EmptyViewConfig(
                                title = stringResource(id = R.string.app_main_screen_empty_favourite_characters_title),
                                description = stringResource(id = R.string.app_main_screen_empty_favourite_characters_description)
                            ),
                            onCharacterClick = {
                                // TODO("Add implementation")
                            }
                        )
                    }
                }
            }
        }
    }
}