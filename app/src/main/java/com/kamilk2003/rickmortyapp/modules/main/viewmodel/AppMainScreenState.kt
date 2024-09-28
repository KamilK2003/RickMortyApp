package com.kamilk2003.rickmortyapp.modules.main.viewmodel

import android.content.Context
import com.kamilk2003.rickmortyapp.R
import com.kamilk2003.rickmortyapp.modules.main.data.Tabs
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.views.EmptyViewConfig

data class AppMainScreenState(
    val characters: List<Character>? = null,
    val favouriteCharacters: List<Character> = emptyList(),
    val isRefreshing: Boolean = true,
    var selectedTab: Tabs = Tabs.ALL
) {
    fun getCharactersList() : List<Character>? {
        return if (selectedTab == Tabs.ALL) getAllCharacters() else getFavouriteCharactersList()
    }

    fun getEmptyViewConfig(
        context: Context,
        onClick: (() -> Unit)? = null
    ): EmptyViewConfig {
        val title = if (selectedTab == Tabs.ALL)
            R.string.app_main_screen_empty_characters_title
            else R.string.app_main_screen_empty_favourite_characters_title
        val description = if (selectedTab == Tabs.ALL)
            R.string.app_main_screen_empty_characters_description
            else R.string.app_main_screen_empty_favourite_characters_description
        val buttonTitle = if (selectedTab == Tabs.ALL)
            R.string.app_main_screen_empty_characters_button_title
            else null

        return EmptyViewConfig(
            title = context.getString(title),
            description = context.getString(description),
            buttonTitle = if (buttonTitle == null) null else context.getString(buttonTitle),
            onClick = if (selectedTab == Tabs.ALL) onClick else null
        )
    }

    // TODO("Move all test tag strings to string.xml")
    fun getTestTag(): String =
        if (selectedTab == Tabs.ALL) "all_characters_list" else "favourites_characters_list"

    fun getFavState(character: Character): Boolean =
        favouriteCharacters.contains(character)

    fun getFavouriteCharactersList(): List<Character>? =
        characters?.filter { getFavState(it) }
    private fun getAllCharacters(): List<Character>? = characters
}