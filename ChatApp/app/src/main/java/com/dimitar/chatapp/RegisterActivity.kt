package com.dimitar.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.dimitar.chatapp.auth.AppModule


class RegisterActivity : AppCompatActivity() {



    var username: String = "mitko"
    var password: String = "alabalamala"

    var usernameChanged: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented") }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            TODO("Not yet implemented")
        }
        override fun afterTextChanged(s: Editable) {
            username = s.toString()
        }
    }

    var passwordChanged: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented") }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            TODO("Not yet implemented")
        }
        override fun afterTextChanged(s: Editable) {
            password = s.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameField = findViewById<EditText>(R.id.Username)
        usernameField.addTextChangedListener(usernameChanged)

        val passwordField = findViewById<EditText>(R.id.Password)
        passwordField.addTextChangedListener(passwordChanged)

        val registerButton = findViewById<Button>(R.id.SignUp)
        registerButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val authAPI = AppModule.provideAuthApi()
        val sharedPreferences = application.getSharedPreferences("prefs", MODE_PRIVATE)
        val repository = AppModule.provideAuthRepository(authAPI, sharedPreferences)
        val result = repository.signUp(
                username = username,
                password = password
            )
    }

}