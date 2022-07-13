package com.example.noticiasapp.ui.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.noticiasapp.R
import com.example.noticiasapp.databinding.ActivityMainBinding
import com.example.noticiasapp.ui.fragments.ArticleFragment
import com.example.noticiasapp.ui.fragments.NewsSavedFragment
import com.example.noticiasapp.ui.fragments.TopHeadlinesFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        binding.imgSave.visibility = View.VISIBLE
                        binding.imgDelete.visibility = View.VISIBLE
                        binding.backbutton.visibility = View.VISIBLE

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

/*    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_guardar -> {
               Toast.makeText(this, "Se preciono1", Toast.LENGTH_LONG).show()
                true
            }
            R.id.nav_eliminar -> {
                Toast.makeText(this, "Se preciono2", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }*/
}