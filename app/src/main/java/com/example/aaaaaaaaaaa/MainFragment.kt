package com.example.aaaaaaaaaaa

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.aaaaaaaaaaa.databinding.MainActivityBinding
import com.example.aaaaaaaaaaa.databinding.MainFragmentBinding

class MainFragment:Fragment(R.layout.main_fragment) {
    lateinit var binding: MainFragmentBinding
    private lateinit var navController: NavController
    @SuppressLint("Recycle")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        binding = MainFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val objectAnimator = ObjectAnimator.ofFloat(binding.back, View.ROTATION,0f,360f).apply {
            duration = 10000
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
        val objectAnimatorXScale = ObjectAnimator.ofFloat(binding.back, View.SCALE_X,1f,2.5f).apply {
            duration = 1
            start()
        }
        val objectAnimatorYScale = ObjectAnimator.ofFloat(binding.back, View.SCALE_Y,1f,2.5f).apply {
            duration = 1
            start()
        }
        binding.enter.setOnClickListener{
            navController.navigate(R.id.journalFragment)
        }
        return binding.root
    }
}