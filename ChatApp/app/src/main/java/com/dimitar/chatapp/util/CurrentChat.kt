package com.dimitar.chatapp.util

import com.dimitar.chatapp.chat.ChatSocketService

class CurrentChat {
    companion object {
        var Id: Int = -1
        var Name: String = ""
        var chatSocketService : ChatSocketService? = null
    }
}
