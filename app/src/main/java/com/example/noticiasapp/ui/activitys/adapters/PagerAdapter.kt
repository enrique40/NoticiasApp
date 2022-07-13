package com.example.noticiasapp.ui.activitys.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.noticiasapp.ui.fragments.TopHeadlinesFragment
import com.example.noticiasapp.ui.fragments.NewsSavedFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { TopHeadlinesFragment() }
            1 -> { NewsSavedFragment() }

            else -> { throw Resources.NotFoundException("Posicion no encontrada")}
        }
    }
}