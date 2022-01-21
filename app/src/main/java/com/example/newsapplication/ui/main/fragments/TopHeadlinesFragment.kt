package com.example.newsapplication.ui.main.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.example.newsapplication.databinding.FragmentTopHeadlinesBinding
import com.example.newsapplication.ui.main.adapters.NewsAdapter
import com.example.newsapplication.ui.main.adapters.diff_utils.NewsDiffUtils
import com.example.newsapplication.ui.main.models.Article
import com.example.newsapplication.ui.main.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopHeadlinesFragment : Fragment() {

    private val newsViewModel by viewModel<NewsViewModel>()

    private val adapter by lazy { NewsAdapter() }

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        val root = binding.root

        newsViewModel.getTopHeadlines()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.topHeadLineLiveData.observe(viewLifecycleOwner, { newsList ->
            setUpAdapter(newsList)
            _binding?.progressBar?.isVisible = false
        })
    }

    private fun setUpAdapter(newsList: List<Article>) {
        val diffUtils = NewsDiffUtils(adapter.getData(), newsList)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        val recyclerViewState: Parcelable? = _binding?.recyclerView?.layoutManager?.onSaveInstanceState()
        adapter.setData(newsList)
        diffResult.dispatchUpdatesTo(adapter)
        _binding?.recyclerView?.layoutManager?.onRestoreInstanceState(recyclerViewState)
        _binding?.recyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}