package com.example.aaaaaaaaaaa

import android.R.attr.defaultValue
import android.R.attr.key
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.aaaaaaaaaaa.databinding.AddPostNotesBinding


class AddPostNoteFragment: Fragment(R.layout.add_post_notes) {
    private lateinit var binding: AddPostNotesBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val title = viewModel.text.value.toString()
        val color = viewModel.color.value!!

        navController = findNavController()
        binding = AddPostNotesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding.save.setOnClickListener{
            navController.navigate(R.id.journalFragment)
        }
        binding.goBack.setOnClickListener{
            navController.popBackStack()
        }
        binding.emotion.text = title
        binding.emotion.setTextColor(color)
        if(color == resources.getColor(R.color.red)){
            binding.sadness.setImageDrawable(requireContext().getDrawable(R.drawable.soft_flower))
            binding.constraintLayout.background = resources.getDrawable(R.drawable.red_grad)
        }
        else if(color == resources.getColor(R.color.blue)){
            binding.sadness.setImageDrawable(requireContext().getDrawable(R.drawable.summertime_sadness))
            binding.constraintLayout.background = resources.getDrawable(R.drawable.blue_grad)
        }
        else if(color == resources.getColor(R.color.yellow)){
            binding.sadness.setImageDrawable(requireContext().getDrawable(R.drawable.lightning_1))
            binding.constraintLayout.background = resources.getDrawable(R.drawable.yellow_grad)
        }
        else{
            binding.sadness.setImageDrawable(requireContext().getDrawable(R.drawable.mithosis))
            binding.constraintLayout.background = resources.getDrawable(R.drawable.green_grad)
        }
        return binding.root
    }
}