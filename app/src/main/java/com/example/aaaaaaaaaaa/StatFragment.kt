package com.example.aaaaaaaaaaa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.aaaaaaaaaaa.databinding.StatFragmentBinding

class StatFragment : Fragment(R.layout.stat_fragment) {
    private lateinit var binding: StatFragmentBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var dateItemsList = listOf(
            DateItemAdapter.DateItem("17-24 фев", true),
            DateItemAdapter.DateItem("3-10 фев", false),
            DateItemAdapter.DateItem("17-24 фев", false),
            DateItemAdapter.DateItem("3-10 фев", false),
            DateItemAdapter.DateItem("1-7 фев", false)
        )
        navController = findNavController()
        binding = StatFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        binding.journal.setOnClickListener {
            navController.navigate(R.id.journalFragment)
        }

        binding.settings.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }

        val viewPager = binding.viewPager
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        val adapter = VerticalPagerAdapter(this)
        viewPager.adapter = adapter


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val dots = listOf(
                    binding.firstDot,
                    binding.secondDot,
                    binding.thirdDot,
                    binding.fourthDot
                )

                dots.forEachIndexed { index, dot ->
                    dot.setColorFilter(
                        if (index == position)
                            this@StatFragment.requireActivity().getColor(R.color.white)
                        else
                            this@StatFragment.requireActivity().getColor(R.color.gray6)
                    )
                }
            }
        })

        val recyclerAdapter = DateItemAdapter(this.requireActivity(), dateItemsList)
        binding.recycler.adapter = recyclerAdapter
        binding.recycler.layoutManager =
            LinearLayoutManager(this.requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }
}