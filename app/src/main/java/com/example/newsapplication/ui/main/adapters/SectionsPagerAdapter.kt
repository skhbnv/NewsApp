package com.example.newsapplication.ui.main.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.newsapplication.R
import com.example.newsapplication.ui.main.fragments.EverythingFragment
import com.example.newsapplication.ui.main.fragments.TopHeadlinesFragment
import java.lang.Exception

private val TAB_TITLES = arrayOf(
    R.string.first_tab_title,
    R.string.second_tab_title
)
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                TopHeadlinesFragment()
            }
            1-> {
                EverythingFragment()
            }
            else ->{
                throw Exception("There is cannot be another fragment")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 2
}