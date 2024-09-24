package com.kamilk2003.rickmortyapp

import android.app.Application
import com.kamilk2003.rickmortyapp.di.servicesModule
import com.kamilk2003.rickmortyapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App : Application() {

    // MARK: - Lifecycle

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    // MARK: - Methods

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)

            val activeModules = mutableListOf<Module>()
            activeModules.addAll(
                listOf(
                    servicesModule,
                    viewModelModule
                )
            )
            modules(activeModules)
        }
    }
}