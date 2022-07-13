package com.example.noticiasapp.ui.fragments

import android.app.Application
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noticiasapp.R
import com.example.noticiasapp.adapters.NewsAdapter
import com.example.noticiasapp.databinding.FragmentTopHeadlinesBinding
import com.example.noticiasapp.models.Article
import com.example.noticiasapp.ui.viewModel.NewsViewModel
import com.example.noticiasapp.util.Constants.Companion.QUERY_PAGE_ZISE
import com.example.noticiasapp.util.OnclickListener
import com.example.noticiasapp.util.Resource
import com.example.noticiasapp.util.providerPreferencias


class TopHeadlinesFragment : Fragment(), OnclickListener {

    private lateinit var _binding: FragmentTopHeadlinesBinding
    val binding get() = _binding

    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    var ultimodat = false
    var scroll = true
    var recyclerViewState: Parcelable? = null
    private lateinit var viewModel: NewsViewModel
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(this)
    }
    lateinit var providerPreferencias: providerPreferencias

    val TAG = "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = NewsViewModel(requireContext().applicationContext as Application)
        providerPreferencias = providerPreferencias(requireContext())
        viewModel.getBreakingNews("us")
        setupRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        Log.e("TAG", "newsResponse $newsResponse: " )
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_ZISE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if (isLastPage) {
                            binding.rvTopHeadlines.setPadding(0,0,0,0)
                        }
                    }

                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                        Toast.makeText(requireContext(), "Un error ocurrido ${message}", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

    }

    private fun hideProgressBar() {
        binding.progress.visibility = View.GONE
        isLoading = false
        if (!isLastPage) {
            binding.progressBarPagin.visibility = View.GONE
        }
    }

    private fun showProgressBar() {
        binding.progress.visibility = View.VISIBLE
        isLoading = true
        if (ultimodat) {
            binding.progressBarPagin.visibility = View.VISIBLE
        }
    }


    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            //para obtener la primera posicion del elemento visible
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            //recuento total de elementos visible
            val visibleItemCount = layoutManager.childCount

            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            //recuento total elementos en nuestra vista
            val totalItemCount = layoutManager.itemCount
            //val conexion = requireContext().isDeviceOnline()
            //binding.progressBarPagin.visibility = View.VISIBLE

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            //Si es mayor o igual que el recuento total de elementos entonces sabemos que nuestro ultimo elemento es visible
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_ZISE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            ultimodat = totalItemCount == lastVisibleItem + 1
            Log.e("TAG", "pag: ${ultimodat}  shouldPaginate -> ${shouldPaginate}" )

            if (shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false

            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTopHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@TopHeadlinesFragment.scrollListener)
        }

    }

    override fun onClick(article: Article) {
        Log.e("TAG", "onClick: $article" )

        val bundle = Bundle().apply {
            putSerializable("article", article)
            putString("origen", "topHeadlines")
        }
        providerPreferencias.set_Nav("topHeadlines")
        findNavController().navigate(R.id.articleFragment, bundle!!)

    }
}