package com.dimitar.chatapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentViewHolder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {
    val authClient = OkHttpClient()
    val authServerUrl = "http://10.0.2.2:6789/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewModel: MainActivityViewModel by viewModels()

        setContentView(R.layout.activity_main)
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
    }
}