package com.kamilk2003.rickmortyapp.di

import com.kamilk2003.rickmortyapp.modules.main.viewmodel.AppMainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AppMainScreenViewModel(
            appCRUDService = get(),
            charactersApiService = get()
        )
    }
}