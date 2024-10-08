package com.dimitar.chatapp.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dimitar.chatapp.data.RegisterRepository

class SignUpViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun sendSignUpReq(username: String, password: String, passwordRe: String){
        if(password == passwordRe){
            val signUpRepository = RegisterRepository(username, password)
            signUpRepository.sendSignUpReq()
        } else{
            Log.d("TEST", "FAILED!!!");
        }

    }
}