package com.example.aaaaaaaaaaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.aaaaaaaaaaa.databinding.SettingsFragmentBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class SettingsFragment:Fragment(R.layout.settings_fragment) {
    private lateinit var binding: SettingsFragmentBinding
    private lateinit var navController: NavController
    @SuppressLint("UseCompatLoadingForColorStateLists", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        binding = SettingsFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        binding.journal.setOnClickListener{
            navController.navigate(R.id.journalFragment)
        }

        binding.stat.setOnClickListener{
            navController.navigate(R.id.statFragment)
        }

        val iosSwitch = binding.iosSwitch
        val iosSwitch2 = binding.iosSwitch2
        val reminding = binding.addReminding

        iosSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                iosSwitch.trackTintList = resources.getColorStateList(R.color.ios_track_on)
                iosSwitch.background = resources.getDrawable(R.drawable.ios_switcher_back_on)
                iosSwitch.thumbDrawable = resources.getDrawable(R.drawable.ios_thumb_on)
            } else {
                iosSwitch.trackTintList = resources.getColorStateList(R.color.ios_track_off)
                iosSwitch.background = resources.getDrawable(R.drawable.ios_switcher_back_off)
                iosSwitch.thumbDrawable = resources.getDrawable(R.drawable.ios_thumb_off)
            }
        }

        iosSwitch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                iosSwitch2.trackTintList = resources.getColorStateList(R.color.ios_track_on)
                iosSwitch2.background = resources.getDrawable(R.drawable.ios_switcher_back_on)
                iosSwitch2.thumbDrawable = resources.getDrawable(R.drawable.ios_thumb_on)
            } else {
                iosSwitch2.trackTintList = resources.getColorStateList(R.color.ios_track_off)
                iosSwitch2.background = resources.getDrawable(R.drawable.ios_switcher_back_off)
                iosSwitch2.thumbDrawable = resources.getDrawable(R.drawable.ios_thumb_off)
            }
        }

        reminding.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val picker = MaterialTimePicker.Builder()
                .setTheme(R.style.Theme_EmotionsApp_TimepickerDialog)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText(R.string.reminding)
                .build()

            picker.addOnPositiveButtonClickListener {
                val selectedHour = picker.hour
                val selectedMinute = picker.minute
            }

            picker.show(this.requireActivity().supportFragmentManager, "TIME_PICKER_TAG")
        }

        return binding.root
    }
}