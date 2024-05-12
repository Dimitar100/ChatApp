package com.dimitar.chatapp.data

import android.util.Log
import com.dimitar.chatapp.ui.home.Chat
import com.dimitar.chatapp.ui.home.HomeViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ChatRepository(
    private val viewModel: HomeViewModel,
    ) {
    private val authClient = OkHttpClient()
    private val authServerUrl = "http://10.0.2.2:6789/"
    private val token = "Bearer ${viewModel.getJWT()}"
    fun getAllChats(){
        var result = ""
        var jsonResult : JsonArray

        val request = Request.Builder().url(authServerUrl +"chat/get/all").header("Authorization", token).build()

        authClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    // viewModel.updateState(response.body!!.string())
                    result = response.body!!.string()
                    val json = Json { ignoreUnknownKeys = true }
                    jsonResult = json.parseToJsonElement(result).jsonArray

                    var allChats : List<Chat> = ArrayList()
                    jsonResult.forEach{
                        val chat = Chat(it.jsonObject["name"]!!.jsonPrimitive.content,
                            it.jsonObject["creatorId"]!!.jsonPrimitive.content,
                            it.jsonObject["id"]!!.jsonPrimitive.content.toInt()
                        )
                        allChats = allChats.plus(chat)
                    }
                    Log.d("REPO", allChats.toString())
                    viewModel.updateState(allChats)

                }else{
                    Log.d("TEST", "request FAILED!!!");
                }
            }
        })
    }

    fun createChat(chatName: String, participantName: String){
        val jsonObject = JSONObject()
        try {
            jsonObject.put("name", chatName)
            jsonObject.put("participant", participantName)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder().url(authServerUrl +"chat/create").header("Authorization", token).post(body).build()


        authClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                   getAllChats()
                }else{
                    Log.d("TEST", "request FAILED!!!");
                }
            }
        })
    }
}