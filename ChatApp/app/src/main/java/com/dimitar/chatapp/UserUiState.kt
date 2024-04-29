package com.dimitar.chatapp

data class UserUiState(
    val isSignedIn: Boolean = false,
)
val UserUiState.isSignedIn: Boolean get() = isSignedIn
