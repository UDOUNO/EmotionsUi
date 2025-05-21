package com.example.aaaaaaaaaaa

import android.R.attr.key
import android.R.attr.value
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.aaaaaaaaaaa.databinding.AddPostFragmentBinding


class AddPostFragment: Fragment(R.layout.add_post_fragment) {
    private lateinit var viewModel: SharedViewModel
    lateinit var binding: AddPostFragmentBinding
    private lateinit var navController: NavController
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        navController = findNavController()
        binding = AddPostFragmentBinding.inflate(layoutInflater)
        binding.goNext.isEnabled = false
        binding.goBack.setOnClickListener{
            navController.navigate(R.id.journalFragment)
        }
        binding.goNext.setOnClickListener{
            navController.navigate(R.id.addPostNoteFragment)
        }
        binding.circleGridView.onCircleSelectionChanged = { i: Int, title: String, color: Int ->
            if(i != -1){
                viewModel.text.value = title
                viewModel.color.value = color
                binding.goNext.isEnabled = true
                binding.goNext.setImageDrawable(resources.getDrawable(R.drawable.arrow_black))
                binding.choose.text = title
                binding.choose.setTextColor(color)
            }
        }
        return binding.root
    }
}