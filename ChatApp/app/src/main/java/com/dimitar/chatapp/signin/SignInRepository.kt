package com.dimitar.chatapp.signin

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.*
import okhttp3.Response

import java.io.IOException

class SignInRepository(
    private val username: String,
    private val password: String
) {
    fun sendSignInReq() : Boolean{
        var result = false
        val authClient = OkHttpClient()
        val authServerUrl = "http://10.0.2.2:6789/"
        val jsonObject = JSONObject()
        try {
            jsonObject.put("username", username)
            jsonObject.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder().url(authServerUrl +"signin").post(body).build()

        authClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    Log.d("TEST", response.body!!.string());

                }
            }
        })

        return result
    }
}