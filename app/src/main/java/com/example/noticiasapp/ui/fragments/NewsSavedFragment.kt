package com.example.noticiasapp.ui.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noticiasapp.R
import com.example.noticiasapp.adapters.NewsAdapter
import com.example.noticiasapp.databinding.FragmentNewsSavedBinding
import com.example.noticiasapp.models.Article
import com.example.noticiasapp.ui.viewModel.NewsViewModel
import com.example.noticiasapp.util.OnclickListener
import com.example.noticiasapp.util.ProviderPreferencias

class NewsSavedFragment : Fragment(), OnclickListener {

    private lateinit var _binding: FragmentNewsSavedBinding
    private val binding get() = _binding
    private lateinit var providerPreferencias: ProviderPreferencias

    private lateinit var viewModel: NewsViewModel
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
        providerPreferencias = ProviderPreferencias(requireContext())
        viewModel = NewsViewModel( requireContext(), requireContext().applicationContext as Application)

        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles.toList())
            binding.rvNewsSaved.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
            }
        }

    }


    override fun onClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        providerPreferencias.setNav("newsSave")
        findNavController().navigate(R.id.articleFragment, bundle)
    }
}