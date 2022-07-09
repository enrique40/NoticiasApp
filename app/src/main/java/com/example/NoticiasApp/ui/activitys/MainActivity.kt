package com.example.NoticiasApp.ui.activitys

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.NoticiasApp.R
import com.example.NoticiasApp.databinding.ActivityMainBinding
import com.example.NoticiasApp.ui.activitys.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, index ->
            tab.text = when(index){
                0 -> { "Titulares Principales"}
                1 -> { "Noticias Guardadas"}

                else -> { throw Resources.NotFoundException("Posicion no encontrada")}
            }

        }.attach()

    }
}