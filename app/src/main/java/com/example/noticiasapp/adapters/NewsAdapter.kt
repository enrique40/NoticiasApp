package com.example.noticiasapp.adapters

import android.annotation.SuppressLint
import android.util.Log
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
            Log.e("TAG", "onBindViewHolder: source.name${article.source?.name} publishedAt ${article.publishedAt}")

        }
    }

    override fun getItemCount(): Int {
        Log.e("TAG", "getItemCount: ${differ.currentList.size}" )
        return differ.currentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        differ.currentList.clear()
        notifyDataSetChanged()
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

    //pasaremos el acticulo actual cuando hagamos click en un elemento a esta funcion lambda para que podamos abrir la pagina de vista web correcta
    /*private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }*/

}