package com.example.newsapplication.ui.main.api

import com.example.newsapplication.BuildConfig.API_KEY
import com.example.newsapplication.ui.main.models.NewsResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/everything")
    fun getEverything(
        @Query("q") query: String = "apple",
    ): Observable<NewsResponse>

    @GET("/v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String = "us",
    ): Observable<NewsResponse>


    companion object{
        operator fun invoke(): NewsApi{
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val requestInterceptor = Interceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()

                val request = chain
                    .request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(requestInterceptor)
                addInterceptor(httpLoggingInterceptor)
            }.build()

            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://newsapi.org")
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
                addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            }.build()

            return retrofit.create(NewsApi::class.java)
        }
    }
}