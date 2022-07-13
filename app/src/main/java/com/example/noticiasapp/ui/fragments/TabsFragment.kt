package com.example.noticiasapp.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noticiasapp.R
import com.example.noticiasapp.databinding.FragmentTabsBinding
import com.example.noticiasapp.databinding.FragmentTopHeadlinesBinding
import com.example.noticiasapp.ui.activitys.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TabsFragment : Fragment() {

    private lateinit var _binding: FragmentTabsBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = PagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, index ->
            tab.text = when(index){
                0 -> { "Titulares Principales"}
                1 -> { "Noticias Guardadas"}

                else -> { throw Resources.NotFoundException("Posicion no encontrada")}
            }

        }.attach()
    }

}