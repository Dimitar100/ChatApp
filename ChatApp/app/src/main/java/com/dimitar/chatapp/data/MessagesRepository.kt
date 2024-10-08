package com.dimitar.chatapp.data

import android.util.Log
import com.dimitar.chatapp.ui.chat.ChatViewModel
import com.dimitar.chatapp.util.CurrentChat
import com.dimitar.chatapp.util.User
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MessagesRepository(
    private val viewModel: ChatViewModel,
)
 {
     private val authClient = OkHttpClient()
     private val authServerUrl = "http://10.0.2.2:6789/"
     private val token = "Bearer ${User.jwt}"

    fun getMessages(){
        var result = ""
        var jsonResult : JsonArray

        val request = Request.Builder().url(authServerUrl +"chat/messages/?chatId="+CurrentChat.Id).header("Authorization", token).build()

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

                    var allMessages : List<Message> = ArrayList()
                    jsonResult.forEach{
                        val message = Message(it.jsonObject["content"]!!.jsonPrimitive.content,
                            it.jsonObject["timestamp"]!!.jsonPrimitive.content,
                            it.jsonObject["senderId"]!!.jsonPrimitive.content,
                            it.jsonObject["id"]!!.jsonPrimitive.content.toInt()
                        )
                        allMessages = allMessages.plus(message)
                    }
                    Log.d("MSG_REPO", allMessages.toString())
                    viewModel.updateState(allMessages)

                }else{
                    Log.d("TEST", "request FAILED!!!")
                }
            }
        })
    }
}