package com.example.newsapplication

import android.app.Application
import com.example.newsapplication.ui.main.di.repositoryModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(listOf(repositoryModel))
        }
    }
}