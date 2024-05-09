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
import com.dimitar.chatapp.data.ChatRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class HomeViewModel(
    private val jwt: String
) : ViewModel() {

    private var _text = MutableLiveData<String>().apply {
        value = jwt
    }
    val text: LiveData<String> = _text

    fun getAllChats(){
        val chatRepo = ChatRepository(this)
        chatRepo.getAllChats()
    }

    fun getJWT(): String {
        return jwt
    }



}