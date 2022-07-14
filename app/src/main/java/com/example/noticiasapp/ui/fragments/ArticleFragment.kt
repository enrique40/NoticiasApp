package com.example.noticiasapp.ui.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.noticiasapp.databinding.FragmentArticleBinding
import com.example.noticiasapp.ui.activitys.MainActivity
import com.example.noticiasapp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment() {

    private lateinit var _binding: FragmentArticleBinding
    private val binding get() = _binding

    private lateinit var viewModel: NewsViewModel
    private lateinit var activityMain: MainActivity

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityMain = activity as MainActivity
        viewModel = NewsViewModel(requireContext(), requireContext().applicationContext as Application)

        val  article = args.article


        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
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

}