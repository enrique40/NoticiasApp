package com.example.noticiasapp.ui.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.noticiasapp.R
import com.example.noticiasapp.databinding.ActivityMainBinding
import com.example.noticiasapp.ui.fragments.ArticleFragment
import com.example.noticiasapp.ui.fragments.NewsSavedFragment
import com.example.noticiasapp.ui.fragments.TopHeadlinesFragment
import com.example.noticiasapp.util.ProviderPreferencias

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var providerPreferencias: ProviderPreferencias


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        providerPreferencias = ProviderPreferencias(this)
          val toolbar = findViewById<Toolbar>(R.id.toolbar)
          setSupportActionBar(toolbar)
          supportActionBar!!.title = ""


        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                TransitionManager.beginDelayedTransition(binding.root)
                when (f) {
                    is TopHeadlinesFragment -> {
                        binding.imgSave.visibility = View.GONE
                        binding.imgDelete.visibility = View.GONE
                        binding.backbutton.visibility = View.GONE

                    }

                    is NewsSavedFragment -> {
                        binding.imgSave.visibility = View.GONE
                        binding.imgDelete.visibility = View.GONE
                        binding.backbutton.visibility = View.GONE
                    }

                    is ArticleFragment -> {
                       val nav = providerPreferencias.getNav()
                        if (nav.equals("topHeadlines")) {
                            binding.imgSave.visibility = View.VISIBLE
                            binding.imgDelete.visibility = View.GONE
                            binding.backbutton.visibility = View.VISIBLE
                        }else {
                            binding.imgSave.visibility = View.GONE
                            binding.imgDelete.visibility = View.VISIBLE
                            binding.backbutton.visibility = View.VISIBLE
                        }
                    }
                    else ->{
                        binding.imgSave.visibility = View.GONE
                        binding.imgDelete.visibility = View.GONE
                        binding.backbutton.visibility = View.GONE

                    }
                }
            }
        }, true)

    }
}