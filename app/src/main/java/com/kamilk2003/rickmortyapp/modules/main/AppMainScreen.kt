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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamilk2003.rickmortyapp.R
import com.kamilk2003.rickmortyapp.modules.main.data.Tabs
import com.kamilk2003.rickmortyapp.modules.main.viewmodel.AppMainScreenAction
import com.kamilk2003.rickmortyapp.modules.main.viewmodel.AppMainScreenViewModel
import com.kamilk2003.rickmortyapp.modules.main.views.CharactersList
import com.kamilk2003.rickmortyapp.modules.main.views.ResponsiveText
import com.kamilk2003.rickmortyapp.ui.theme.appTypo
import com.kamilk2003.rickmortyapp.ui.theme.dimens
import com.kamilk2003.rickmortyapp.views.EmptyViewConfig
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMainScreen(viewModel: AppMainScreenViewModel = koinViewModel()) {

    // MARK: - Stored Properties

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val context = LocalContext.current

    var selectedTab by rememberSaveable { mutableStateOf(Tabs.ALL) }
    val pageState = rememberPagerState(initialPage = selectedTab.ordinal) { 2 }

    // MARK: - Launched Effects

    LaunchedEffect(Unit) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is AppMainScreenAction.IsFavouriteFlagChanged -> {
                    snackbarState.showSnackbar(
                        if (action.isFavourite) context.getString(R.string.app_main_screen_snackbar_added_to_favourite)
                        else context.getString(R.string.app_main_screen_snackbar_deleted_from_favourite)
                    )
                }
            }
        }
    }

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
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
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
                modifier = Modifier.fillMaxSize(),
                isRefreshing = state.isRefreshing,
                onRefresh = {
                    viewModel.changeRefreshingState(true)
                    viewModel.fetchCharacters()
                },
                contentAlignment = Alignment.Center
            ) {
                when (selectedTab) {
                    Tabs.ALL -> {
                        CharactersList(
                            scrollBehavior = scrollBehavior,
                            characters = state.characters,
                            isFavouriteCharacter = state::isFavourite,
                            emptyViewConfig = EmptyViewConfig(
                                title = stringResource(id = R.string.app_main_screen_empty_characters_title),
                                description = stringResource(id = R.string.app_main_screen_empty_characters_description),
                                buttonTitle = stringResource(id = R.string.app_main_screen_empty_characters_button_title),
                                onClick = {
                                    viewModel.changeRefreshingState(true)
                                    viewModel.fetchCharacters()
                                }
                            ),
                            onCharacterClick = viewModel::manageFavouriteCharacters
                        )
                    }
                    Tabs.FAVOURITE -> {
                        CharactersList(
                            scrollBehavior = scrollBehavior,
                            characters = state.favouriteCharacters,
                            isFavouriteCharacter = state::isFavourite,
                            emptyViewConfig = EmptyViewConfig(
                                title = stringResource(id = R.string.app_main_screen_empty_favourite_characters_title),
                                description = stringResource(id = R.string.app_main_screen_empty_favourite_characters_description)
                            ),
                            onCharacterClick = viewModel::manageFavouriteCharacters
                        )
                    }
                }
            }
        }
    }
}