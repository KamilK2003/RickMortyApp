package com.kamilk2003.rickmortyapp.modules.main.viewmodel

sealed class AppMainScreenAction {
    data class FavouriteFlagChanged(val isFavourite: Boolean): AppMainScreenAction()
}