package com.dimitar.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient


class LoginActivity : AppCompatActivity() {
    val authClient = OkHttpClient()
    val authServerUrl = "http://10.0.2.2:6789/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewModel: MainActivityViewModel by viewModels()

        setContentView(R.layout.activity_login)
        val text = findViewById<TextView>(R.id.testText)
        val textSwitch = findViewById<TextView>(R.id.textSwitch)
        val loginFragment = findViewById<View>(R.id.signInFragment)
        val registerFragment = findViewById<View>(R.id.signUpFragment)
        registerFragment.visibility = View.GONE
        text.text = "Don't have an account? "

        textSwitch.setOnClickListener {
            if(textSwitch.text.toString() == "Register"){
                loginFragment.visibility = View.GONE
                registerFragment.visibility = View.VISIBLE
                textSwitch.text = "Login"
                text.text = "Already have an account? "
            }else{
                registerFragment.visibility = View.GONE
                loginFragment.visibility = View.VISIBLE
                textSwitch.text = "Register"
                text.text = "Don't have an account? "
            }
        }

        val intent = Intent(this, MainActivity::class.java)
// To pass any data to next activity
        //intent.putExtra("keyIdentifier", value)
// start your next activity
        startActivity(intent)
    }
}