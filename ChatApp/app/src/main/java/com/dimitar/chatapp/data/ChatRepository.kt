package com.dimitar.chatapp.data

import android.util.Log
import com.dimitar.chatapp.ui.home.HomeViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ChatRepository(
    private val viewModel: HomeViewModel,
    ) {

    fun getAllChats(){
        var result = ""
        val authClient = OkHttpClient()
        val authServerUrl = "http://10.0.2.2:6789/"

        val token = "Bearer ${viewModel.getJWT()}"
        val request = Request.Builder().url(authServerUrl +"chat/get/all").header("Authorization", token).build()

        authClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    // viewModel.updateState(response.body!!.string())
                    result = response.body!!.string()
                    //val json = Json { ignoreUnknownKeys = true }
                  //  val jsonObject = json.parseToJsonElement(result).jsonArray
                    Log.d("HOME", result);

                }else{
                    Log.d("TEST", "Login request FAILED!!!");
                }
            }
        })
    }
}