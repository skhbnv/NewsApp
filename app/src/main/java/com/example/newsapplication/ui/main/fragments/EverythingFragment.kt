package com.example.newsapplication.ui.main.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.example.newsapplication.databinding.FragmentEverythingPageBinding
import com.example.newsapplication.ui.main.adapters.NewsAdapter
import com.example.newsapplication.ui.main.adapters.diff_utils.NewsDiffUtils
import com.example.newsapplication.ui.main.models.Article
import com.example.newsapplication.ui.main.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.os.Parcelable





class EverythingFragment : Fragment() {

    private val newsViewModel by viewModel<NewsViewModel>()

    private var _binding: FragmentEverythingPageBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { NewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEverythingPageBinding.inflate(inflater, container, false)
        val root = binding.root
        newsViewModel.getEverything()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.swipeToRefresh?.setOnRefreshListener {
            newsViewModel.getEverything()
        }
        newsViewModel.everythingNewsLiveData.observe(viewLifecycleOwner, {
            _binding?.swipeToRefresh?.isRefreshing = false
            setUpAdapter(it)
        })
    }

    private fun setUpAdapter(it: List<Article>) {
        val diffUtils = NewsDiffUtils(adapter.getData(), it)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        val recyclerViewState: Parcelable? = _binding?.recyclerView?.layoutManager?.onSaveInstanceState()
        adapter.setData(it)
        diffResult.dispatchUpdatesTo(adapter)
        _binding?.recyclerView?.layoutManager?.onRestoreInstanceState(recyclerViewState)
        _binding?.recyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}