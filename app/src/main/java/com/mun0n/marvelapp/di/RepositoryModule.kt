package com.mun0n.marvelapp.di

import com.mun0n.marvelapp.repository.DetailRepository
import com.mun0n.marvelapp.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { MainRepository(get()) }

    single { DetailRepository(get()) }
}