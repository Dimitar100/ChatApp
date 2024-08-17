package com.dimitar.chatapp.util

data class UserUiState(
    val isSignedIn: Boolean = false,
)
val UserUiState.isSignedIn: Boolean get() = isSignedIn
