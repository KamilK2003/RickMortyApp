package com.kamilk2003.rickmortyapp.di

import com.kamilk2003.rickmortyapp.services.api.CharactersApiService
import com.kamilk2003.rickmortyapp.services.api.CharactersApiServiceImpl
import com.kamilk2003.rickmortyapp.services.room.AppCRUDService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val servicesModule = module {
    single<CharactersApiService> { CharactersApiServiceImpl() }
    single { AppCRUDService(androidContext()) }
}