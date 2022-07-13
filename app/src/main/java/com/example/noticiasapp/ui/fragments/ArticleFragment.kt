package com.example.noticiasapp.ui.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.noticiasapp.databinding.FragmentArticleBinding
import com.example.noticiasapp.ui.activitys.MainActivity
import com.example.noticiasapp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment() {

    private lateinit var _binding: FragmentArticleBinding
    val binding get() = _binding

    lateinit var viewModel: NewsViewModel
    lateinit var activityMain: MainActivity

    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityMain = activity as MainActivity
        //showProgressBar()
        viewModel = NewsViewModel(requireContext().applicationContext as Application)
        val  article = args.article

        Log.e("TAG", "article: ${article.url!!}")
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
            //hideProgressBar()
        }



        activityMain.binding.backbutton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        activityMain.binding.imgSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Articulo guardado exitosamente", Snackbar.LENGTH_LONG).show()
        }

        activityMain.binding.imgDelete.setOnClickListener {
            viewModel.deleteArticle(article)
            Snackbar.make(view, "Articulo eliminado con exito", Snackbar.LENGTH_LONG).apply {
                setAction("Undo"){
                    viewModel.saveArticle(article)
                }
                show()
            }
            requireActivity().onBackPressed()

        }

    }

    private fun hideProgressBar() {
        //binding.progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        //binding.progress.visibility = View.VISIBLE
    }
}