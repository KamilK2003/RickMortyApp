package com.kamilk2003.rickmortyapp.modules.main.viewmodel

interface AppMainScreenAction {
    class IsFavouriteFlagChanged(val isFavourite: Boolean): AppMainScreenAction
}