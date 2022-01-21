package com.example.newsapplication.ui.main.repositories.network

import com.example.newsapplication.ui.main.api.NewsApi

class NewsRepository(private var api: NewsApi) {
    fun getEverything() = api.getEverything()
    fun getTopHeadlines() = api.getTopHeadlines()
}