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
import kotlinx.serialization.json.Json
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
        var result = ""
        val authClient = OkHttpClient()
        val authServerUrl = "http://10.0.2.2:6789/"

        val token = "Bearer $jwt"
        val request = Request.Builder().url(authServerUrl +"chat/get/all").header("Authorization", token).build()

        authClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    // viewModel.updateState(response.body!!.string())
                    Log.d("HOME", response.body!!.string());

                }else{
                    Log.d("TEST", "Login request FAILED!!!");
                }
            }
        })
    }

}