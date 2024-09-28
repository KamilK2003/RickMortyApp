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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamilk2003.rickmortyapp.R
import com.kamilk2003.rickmortyapp.modules.main.data.Tabs
import com.kamilk2003.rickmortyapp.modules.main.viewmodel.AppMainScreenAction
import com.kamilk2003.rickmortyapp.modules.main.viewmodel.AppMainScreenViewModel
import com.kamilk2003.rickmortyapp.modules.main.views.CharactersList
import com.kamilk2003.rickmortyapp.views.ResponsiveText
import com.kamilk2003.rickmortyapp.ui.theme.appTypo
import com.kamilk2003.rickmortyapp.ui.theme.dimens
import com.kamilk2003.rickmortyapp.views.EmptyView
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

    val pageState = rememberPagerState(initialPage = state.selectedTab.ordinal) { 2 }

    // MARK: - Launched Effects

    LaunchedEffect(Unit) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is AppMainScreenAction.FavouriteFlagChanged -> {
                    snackbarState.showSnackbar(
                        if (action.isFavourite) context.getString(R.string.app_main_screen_snackbar_added_to_favourite)
                        else context.getString(R.string.app_main_screen_snackbar_deleted_from_favourite)
                    )
                }
            }
        }
    }

    LaunchedEffect(state.selectedTab) {
        pageState.animateScrollToPage(state.selectedTab.ordinal)
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
                    modifier = Modifier
                        .testTag("info_snackbar"),
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
                selectedTabIndex = state.selectedTab.ordinal
            ) {
                Tabs.entries.forEach { tab ->
                    Tab(
                        modifier = Modifier
                            .fillMaxWidth(
                                if (tab.ordinal == 0) MaterialTheme.dimens.weight0_5x
                                else MaterialTheme.dimens.weight1x
                            )
                            .testTag(tab.name),
                        selected = state.selectedTab == tab,
                        onClick = { viewModel.onTabChange(tab = tab) },
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
                if (state.getCharactersList() == null) {
                    EmptyView(
                        modifier = Modifier.testTag("null_characters_list"),
                        emptyViewConfig = EmptyViewConfig(
                            title = stringResource(id = R.string.app_main_screen_downloading_characters_title),
                            description = stringResource(id = R.string.app_main_screen_downloading_characters_description)
                        )
                    )
                } else if (state.getCharactersList()?.isEmpty() == true) {
                    EmptyView(
                        modifier = Modifier.testTag("empty_characters_list"),
                        emptyViewConfig = state.getEmptyViewConfig(
                            context = context,
                            onClick = {
                                viewModel.changeRefreshingState(true)
                                viewModel.fetchCharacters()
                            }
                        )
                    )
                } else {
                    CharactersList(
                        modifier = Modifier.testTag(state.getTestTag()),
                        scrollBehavior = scrollBehavior,
                        characters = state.getCharactersList().orEmpty(),
                        favouriteCharacters = state.getFavouriteCharactersList().orEmpty(),
                        onCharacterClick = viewModel::switchFavouriteState
                    )
                }
            }
        }
    }
}