package com.dimitar.chatapp.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimitar.chatapp.data.SignInRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SignInViewModel() : ViewModel() {

    // TODO: Implement the ViewModel
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun updateState(token: String) {
       fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                //val newsItems = repository.newsItemsForCategory(category)
                _uiState.update {
                  it.copy(true, token)
                }
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.

            }
        }
    }
    fun sendSignInReq(username: String, password: String){
        val signInRepository = SignInRepository(this,username, password)
        val token = signInRepository.sendSignInReq()

    }

    fun updateUiState(token: String){
        if (token != ""){
            //Log.d("TEST", token)
            updateState(token)
        }else{
            Log.d("TEST", "Login Failed!")
        }
    }


}