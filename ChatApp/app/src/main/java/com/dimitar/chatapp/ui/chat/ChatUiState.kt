package com.dimitar.chatapp.ui.chat

import com.dimitar.chatapp.data.Message


data class ChatUiState(
    var messages: List<Message> = ArrayList()
)
