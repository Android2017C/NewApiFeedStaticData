package com.cnureddy.newapifeedstaticdata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cnureddy.newapifeedstaticdata.databinding.NewsItemBinding
import com.cnureddy.newapifeedstaticdata.model.ArticlesItem
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class NewsAdapter(private val context: Context, private var listArticlesItem: List<ArticlesItem>) :
    ListAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(DiffUtils()) {
    private lateinit var binding: NewsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var articlesItem = listArticlesItem[position]
        holder.bind(articlesItem)
        binding.cardView.setOnClickListener {
            openBrowserURL(articlesItem.url)
        }
    }

    override fun getItemCount(): Int {
        return listArticlesItem.size
    }

    fun setArticlesData(articlesItemData: List<ArticlesItem>) {
        this.listArticlesItem = articlesItemData
        notifyDataSetChanged()
    }

    class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articlesItem: ArticlesItem) {
            binding.articles = articlesItem
        }
    }

    private fun openBrowserURL(url: String?) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        openURL.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.applicationContext.startActivity(openURL);
    }

    class DiffUtils : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }

    }
}
