package com.example.newsapplication.ui.main.di

import com.example.newsapplication.NewsApplication
import com.example.newsapplication.ui.main.api.NewsApi
import com.example.newsapplication.ui.main.repositories.network.NewsRepository
import com.example.newsapplication.ui.main.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModel = module {
    single { NewsApi() }
    single { NewsApplication() }
    factory { NewsRepository(get()) }
    viewModel { NewsViewModel(get(), get()) }
}