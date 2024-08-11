package com.dimitar.chatapp.ui.signin

data class SignInUiState(
    var isSignedIn: Boolean = false,
    var jwtToken: String = ""
)
