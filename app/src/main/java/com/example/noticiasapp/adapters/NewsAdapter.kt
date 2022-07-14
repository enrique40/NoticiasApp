package com.example.noticiasapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.noticiasapp.databinding.HeadlineListItemBinding
import com.example.noticiasapp.models.Article
import com.example.noticiasapp.util.OnclickListener

class NewsAdapter(
    private var listener: OnclickListener,
    ) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private var mLastClickTime = System.currentTimeMillis()
    private val clickTimeInterval: Long = 300

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = HeadlineListItemBinding.inflate(LayoutInflater.from(parent.context) , parent,false)
        return ArticleViewHolder(view)

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.viewBinding.imgHeadline)
            holder.viewBinding.textTitle.text = article.title
            holder.viewBinding.textSource.text = article.description

            holder.setListener(article)

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ArticleViewHolder(var viewBinding: HeadlineListItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun setListener(article: Article){
            with(viewBinding.root){
                setOnClickListener {
                    val now = System.currentTimeMillis()
                    when(now - mLastClickTime < clickTimeInterval){
                        true -> {
                            return@setOnClickListener
                        }
                        false -> {
                            mLastClickTime = now
                            listener.onClick(article)
                        }
                    }

                }
            }
        }
    }

}