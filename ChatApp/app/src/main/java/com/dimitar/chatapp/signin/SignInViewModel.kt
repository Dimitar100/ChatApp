package com.dimitar.chatapp.signin

import android.util.Log
import androidx.lifecycle.ViewModel

class SignInViewModel() : ViewModel() {

    // TODO: Implement the ViewModel
    fun sendSignInReq(username: String, password: String){
        val signInRepository = SignInRepository(username, password)
        signInRepository.sendSignInReq()
    }
}