package com.kamilk2003.rickmortyapp.di

import com.kamilk2003.rickmortyapp.service.api.CharactersApiService
import com.kamilk2003.rickmortyapp.service.api.CharactersApiServiceImpl
import org.koin.dsl.module

val servicesModule = module {
    single<CharactersApiService> { CharactersApiServiceImpl() }
}