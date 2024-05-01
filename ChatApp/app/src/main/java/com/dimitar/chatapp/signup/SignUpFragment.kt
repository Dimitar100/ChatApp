package com.dimitar.chatapp.signup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.dimitar.chatapp.R
import com.dimitar.chatapp.signin.SignInViewModel

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        val button = requireView().findViewById(R.id.registerBtn) as Button
        val username = requireView().findViewById(R.id.editTextUsername2) as EditText
        val password = requireView().findViewById(R.id.editTextPassword2) as EditText
        val passwordRe = requireView().findViewById(R.id.editTextPasswordRe) as EditText


        button.setOnClickListener{
            viewModel.sendSignUpReq(username.text.toString(), password.text.toString(), passwordRe.text.toString())
        }
    }
}