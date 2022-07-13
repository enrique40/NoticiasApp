package com.example.noticiasapp.ui.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noticiasapp.R
import com.example.noticiasapp.adapters.NewsAdapter
import com.example.noticiasapp.databinding.FragmentNewsSavedBinding
import com.example.noticiasapp.models.Article
import com.example.noticiasapp.ui.viewModel.NewsViewModel
import com.example.noticiasapp.util.OnclickListener
import com.example.noticiasapp.util.providerPreferencias

class NewsSavedFragment : Fragment(), OnclickListener {

    private lateinit var _binding: FragmentNewsSavedBinding
    val binding get() = _binding
    lateinit var providerPreferencias: providerPreferencias

    lateinit var viewModel: NewsViewModel
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        providerPreferencias = providerPreferencias(requireContext())
        viewModel = NewsViewModel(requireContext().applicationContext as Application)
        setupRecyclerView()

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            Log.e("newsave", "onViewCreated: ${articles.size}" )
            newsAdapter.differ.submitList(articles)
        })

    }

    private fun setupRecyclerView() {
        binding.rvNewsSaved.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun onClick(article: Article) {
        Log.e("TAG", "onClick: $article" )

        val bundle = Bundle().apply {
            putSerializable("article", article)
            putString("origen", "newsSave")
        }
        providerPreferencias.set_Nav("newsSave")
        findNavController().navigate(R.id.articleFragment, bundle!!)
    }
}