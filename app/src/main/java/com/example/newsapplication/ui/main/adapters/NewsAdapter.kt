package com.example.newsapplication.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.databinding.NewsItemBinding
import com.example.newsapplication.ui.main.models.Article
import com.squareup.picasso.Picasso

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var dataList : List<Article> = listOf()

    inner class ViewHolder(private var binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            with(binding){
                title.text = dataList[position].title
                description.text = dataList[position].description

                Picasso
                    .get()
                    .load(dataList[position].urlToImage)
                    .fit()
                    .centerCrop()
                    .into(backgroundImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = dataList.size
    fun setData(newList: List<Article>) {
        dataList = newList
    }
    fun getData() = dataList
}