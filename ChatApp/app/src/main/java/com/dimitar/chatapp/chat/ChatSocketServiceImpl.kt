package com.dimitar.chatapp.chat

import android.util.Log
import com.dimitar.chatapp.data.Message
import com.dimitar.chatapp.data.dto.MessageDto
import com.dimitar.chatapp.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client: HttpClient
): ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?username=$username")
            }
            if(socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String, chatId: Int) {
        try {
            //Temp msg as string {"chatId":20,"message":"Hello world"}

            val frame = "{\"chatId\":$chatId,\"message\":\"$message\"}"
            Log.d("CHAT_FRAGMENT", frame)
            socket?.send(Frame.Text(frame))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> {
        var messageDto : MessageDto
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto.toMessage()

                } ?: flow {  }

        } catch(e: Exception) {
            e.printStackTrace()
            flow {  }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}