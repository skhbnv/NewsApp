package com.example.newsapplication.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.R
import com.example.newsapplication.ui.main.models.Article
import com.example.newsapplication.ui.main.models.NewsResponse
import com.example.newsapplication.ui.main.repositories.network.NewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Timed
import java.util.concurrent.TimeUnit

class NewsViewModel(private var newsRepository: NewsRepository, application: Application) :
    AndroidViewModel(application) {
    val everythingNewsLiveData: LiveData<List<Article>> get() = _everythingNewsLiveData
    private var _everythingNewsLiveData = MutableLiveData<List<Article>>()

    val topHeadLineLiveData: LiveData<List<Article>> get() = _topHeadLineLiveData
    private var _topHeadLineLiveData = MutableLiveData<List<Article>>()

    val showErrorViewModel: LiveData<String> get() = _showErrorViewModel
    private var _showErrorViewModel = MutableLiveData<String>()

    fun getEverything() = newsRepository.getEverything()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getEverythingNewsObserver())

    private fun getEverythingNewsObserver(): Observer<NewsResponse> {
        return object : Observer<NewsResponse> {
            override fun onSubscribe(d: Disposable?) {}

            override fun onNext(reponse: NewsResponse?) {
                _everythingNewsLiveData.value = reponse?.articles
            }

            override fun onError(e: Throwable?) {
                handleError(e)
            }

            override fun onComplete() {}
        }
    }

    fun getTopHeadlines(): Disposable =
        Observable
            .interval(5, TimeUnit.SECONDS, Schedulers.io())
            .doOnNext {
                newsRepository.getTopHeadlines()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getTopHeadlinerObserver())
            }
            .subscribe()

    private fun getTopHeadlinerObserver(): Observer<NewsResponse> {
        return object : Observer<NewsResponse> {
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onNext(reponse: NewsResponse?) {
                _topHeadLineLiveData.value = reponse?.articles
            }

            override fun onError(e: Throwable?) {
                handleError(e)
            }

            override fun onComplete() {
            }
        }
    }

    private fun handleError(throwable: Throwable?) {
        throwable?.let { e ->
            if (!e.message.isNullOrBlank()) {
                _showErrorViewModel.value = e.message
            } else if (!e.localizedMessage.isNullOrBlank()) {
                _showErrorViewModel.value = e.localizedMessage
            } else {
                _showErrorViewModel.value =
                    getApplication<NewsApplication>().resources.getString(R.string.generic_error)
            }
        } ?: kotlin.run {
            _showErrorViewModel.value =
                getApplication<NewsApplication>().resources.getString(R.string.generic_error)
        }
    }
}