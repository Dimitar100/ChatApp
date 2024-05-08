package com.dimitar.chatapp.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dimitar.chatapp.MainActivity
import com.dimitar.chatapp.R
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        viewModel = ViewModelProvider(requireActivity()).get(SignInViewModel::class.java)

        lifecycleScope.launch {
            viewModel.uiState.collect {
               // Log.d("TEST", it.jwtToken)
                if(it.isSignedIn){
                    val intent = Intent(
                        activity,
                        MainActivity::class.java
                    )
                    intent.putExtra("JWT", it.jwtToken)
                    startActivity(intent)
                }

            }
        }



        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        val button = requireView().findViewById(R.id.loginBtn) as Button
        val username = requireView().findViewById(R.id.editTextUsername) as EditText
        val password = requireView().findViewById(R.id.editTextPassword) as EditText

        button.setOnClickListener{
            viewModel.sendSignInReq(username.text.toString(), password.text.toString())
        }
       // viewModel = ViewModelProvider(requireActivity()).get(SignInViewModel::class.java)
    }
}