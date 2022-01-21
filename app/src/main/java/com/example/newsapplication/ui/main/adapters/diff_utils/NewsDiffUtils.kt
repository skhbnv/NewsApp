package com.example.newsapplication.ui.main.adapters.diff_utils


import androidx.recyclerview.widget.DiffUtil
import com.example.newsapplication.ui.main.models.Article


class NewsDiffUtils(private val oldList: List<Article>, private val newList: List<Article>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: Article = oldList[oldItemPosition]
        val newProduct: Article = newList[newItemPosition]
        return oldProduct.title == newProduct.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: Article = oldList[oldItemPosition]
        val newProduct: Article = newList[newItemPosition]
        return (oldProduct.description == newProduct.description
                && oldProduct.content == newProduct.content)
    }

}