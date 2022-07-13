package com.example.noticiasapp.util

import com.example.noticiasapp.models.Article

interface OnclickListener {
    fun onClick(article: Article)
}