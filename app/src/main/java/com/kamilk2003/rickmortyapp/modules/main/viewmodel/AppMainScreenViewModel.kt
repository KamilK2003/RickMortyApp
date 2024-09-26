package com.kamilk2003.rickmortyapp.modules.main.viewmodel

import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.kamilk2003.rickmortyapp.application.base.BaseViewModel
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter
import com.kamilk2003.rickmortyapp.services.api.CharactersApiService
import com.kamilk2003.rickmortyapp.services.room.AppCRUDService
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
            charactersApiService.getCharacters { characters ->
                updateState { currentState ->
                    currentState.copy(
                        characters = characters ?: emptyList(),
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

    fun manageFavouriteCharacters(character: Character) {
        viewModelScope.launch {
            val isFavourite = currentState.isFavourite(character)
            if (isFavourite) {
                appCRUDService.deleteCharacter(character.id.toInt())
            } else {
                appCRUDService.insertCharacter(RoomCharacter(character))
            }

            sendAction(AppMainScreenAction.IsFavouriteFlagChanged(isFavourite.not()))
        }
    }

    private fun observeFavouriteCharacters() { // TODO("Think about how to handle the absence of a favorite character in the list coming from the API")
        viewModelScope.launch {
            appCRUDService.allCharacters.asFlow().collectLatest { characters ->
                updateState { currentState ->
                    currentState.copy(favouriteCharacter = characters ?: emptyList())
                }
            }
        }
    }
}