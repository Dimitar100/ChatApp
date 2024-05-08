package com.dimitar.chatapp.ui.home

import android.app.Application
import android.media.AudioMetadata.Key
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class HomeViewModel(
    private val jwt: String
) : ViewModel() {

    private var _text = MutableLiveData<String>().apply {
        value = jwt
    }
    val text: LiveData<String> = _text

    fun getAllChats(){

    }

}