package com.mun0n.marvelapp

import android.app.Application
import com.mun0n.marvelapp.di.networkModule
import com.mun0n.marvelapp.di.repositoryModule
import com.mun0n.marvelapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelApplication)
            modules(networkModule)
            modules(viewModelModule)
            modules(repositoryModule)
        }
    }
}