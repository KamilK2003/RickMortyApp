package com.kamilk2003.rickmortyapp.modules.main.viewmodel

import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.kamilk2003.rickmortyapp.application.base.BaseViewModel
import com.kamilk2003.rickmortyapp.modules.main.data.Tabs
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter
import com.kamilk2003.rickmortyapp.services.api.CharactersApiService
import com.kamilk2003.rickmortyapp.services.room.AppCRUDService
import com.kamilk2003.rickmortyapp.utils.general.notContains
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppMainScreenViewModel(
    private val appCRUDService: AppCRUDService,
    private val charactersApiService: CharactersApiService
): BaseViewModel<AppMainScreenState, AppMainScreenAction>() {

    // MARK: - Initializers

    init {
        fetchCharacters()
        observeFavouriteCharacters()
    }

    // MARK: - Methods

    override fun initState() = AppMainScreenState()

    fun fetchCharacters() {
        viewModelScope.launch {
            charactersApiService.getCharacters { fetchedCharacters ->
                updateState { currentState ->
                    val characters = fetchedCharacters ?: emptyList()

                    if (characters.isNotEmpty()) {
                        val deprecatedFavoriteCharacters = appCRUDService.allCharacters.value
                            ?.filter { characters.notContains(it) } ?: emptyList()

                        appCRUDService.deleteCharacters(deprecatedFavoriteCharacters)
                    }

                    currentState.copy(
                        characters = characters,
                        isRefreshing = false
                    )
                }
            }
        }
    }

    fun changeRefreshingState(isRefreshing: Boolean) {
        updateState { currentState ->
            currentState.copy(isRefreshing = isRefreshing)
        }
    }

    fun switchFavouriteState(character: Character) {
        viewModelScope.launch {
            val isFavourite = currentState.getFavState(character)
            if (isFavourite) {
                appCRUDService.deleteCharacter(character.id.toInt())
            } else {
                appCRUDService.insertCharacter(RoomCharacter(character))
            }

            sendAction(AppMainScreenAction.FavouriteFlagChanged(isFavourite.not()))
        }
    }

    fun onTabChange(tab: Tabs) {
        updateState {
            it.copy(selectedTab = tab)
        }
    }

    private fun observeFavouriteCharacters() {
        viewModelScope.launch {
            appCRUDService.allCharacters
                .asFlow()
                .collectLatest { fetchedCharacters ->
                    val characters = fetchedCharacters ?: emptyList()
                    updateState { currentState ->
                        currentState.copy(favouriteCharacters = characters)
                    }
                }
        }
    }
}