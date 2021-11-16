package com.torres.demo.di

import com.torres.demo.viewModel.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModel = module{
    viewModel { HomeViewModel(repository = get()) }
}