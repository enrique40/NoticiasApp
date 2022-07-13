package com.example.noticiasapp.repository

import android.content.Context
import com.example.noticiasapp.api.RetrofitInstance
import com.example.noticiasapp.db.ArticleDatabase
import com.example.noticiasapp.models.Article
import com.example.noticiasapp.ui.fragments.ArticleFragment

class NewsRepository(
    context: Context
) {

    private val db = ArticleDatabase.getDatabase(context)

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}