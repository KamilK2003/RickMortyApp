package com.kamilk2003.rickmortyapp.modules.main.viewmodel

import com.kamilk2003.rickmortyapp.objects.models.Character

data class AppMainScreenState(
    val characters: List<Character>? = null,
    val favouriteCharacters: List<Character> = emptyList(),
    val isRefreshing: Boolean = true
) {
    fun isFavourite(character: Character): Boolean = favouriteCharacters.contains(character)
}