package com.dimitar.chatapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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


class MainActivity : AppCompatActivity() {

    val authClient = OkHttpClient()
    val authServerUrl = "http://10.0.2.2:6789/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.testText)



        val jsonObject = JSONObject()
        try {
            jsonObject.put("username", "ganko")
            jsonObject.put("password", "testings")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)


        val request = Request.Builder().url(authServerUrl +"signup").post(body).build()
       // val request = Request.Builder().url("https://reqres.in/api/users?page=2").build()
        //val request = Request.Builder().url("http://10.0.2.2:6789/").build()

        //someTask(authClient, request).execute()


        authClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    runOnUiThread{
                        text.text = response.body!!.string()
                    }
                }
            }
        })
    }
}