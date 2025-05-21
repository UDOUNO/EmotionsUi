package com.example.aaaaaaaaaaa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val text = MutableLiveData<String>()
    val color = MutableLiveData<Int>()
}