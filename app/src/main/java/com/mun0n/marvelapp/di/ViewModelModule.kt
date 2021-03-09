package com.mun0n.marvelapp.di

import com.mun0n.marvelapp.ui.detail.DetailViewModel
import com.mun0n.marvelapp.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { (id: String) ->
        DetailViewModel(
            get(),
            id
        )
    }
}