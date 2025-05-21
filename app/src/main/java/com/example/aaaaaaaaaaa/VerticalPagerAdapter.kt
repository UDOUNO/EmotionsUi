package com.example.aaaaaaaaaaa

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class VerticalPagerAdapter(fragmentActivity: StatFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoryFragment()
            1 -> WeekFragment()
            2 -> OftenFragment()
            3 -> DayFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}