package com.example.aaaaaaaaaaa

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.aaaaaaaaaaa.databinding.JournalFragmentBinding

class JournalFragment: Fragment(R.layout.journal_fragment) {
    private lateinit var viewModel: SharedViewModel
    lateinit var binding: JournalFragmentBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        navController = findNavController()
        binding = JournalFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding.addPost.setOnClickListener{
            navController.navigate(R.id.addPostFragment)
        }
        binding.settings.setOnClickListener{
            navController.navigate(R.id.settingsFragment)
        }
        binding.stat.setOnClickListener {
            navController.navigate(R.id.statFragment)
        }
        binding.card1.setOnClickListener {
            viewModel.text.value = binding.textView.text.toString()
            viewModel.color.value =  0xFF33BBFF.toInt()
            navController.navigate(R.id.addPostNoteFragment)
        }
        binding.card2.setOnClickListener {
            viewModel.text.value = binding.textView2.text.toString()
            viewModel.color.value =  0xFF33FF77.toInt()
            navController.navigate(R.id.addPostNoteFragment)
        }
        binding.card3.setOnClickListener {
            viewModel.text.value = binding.textView3.text.toString()
            viewModel.color.value =  0xFFFFBB33.toInt()
            navController.navigate(R.id.addPostNoteFragment)
        }
        binding.card4.setOnClickListener {
            viewModel.text.value = binding.textView4.text.toString()
            viewModel.color.value = 0xFFFF3333.toInt()
            navController.navigate(R.id.addPostNoteFragment)
        }

        return binding.root
    }
}