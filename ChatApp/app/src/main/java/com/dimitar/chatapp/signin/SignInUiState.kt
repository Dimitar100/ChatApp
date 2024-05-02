package com.dimitar.chatapp.signin

data class SignInUiState(
    var isSignedIn: Boolean = false,
    var jwtToken: String = ""
)
