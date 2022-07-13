package com.example.noticiasapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noticiasapp.repository.NewsRepository
import com.example.noticiasapp.ui.viewModel.NewsViewModel

class NewsViewModelProviderFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

   /* override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel() as T
    }*/
}