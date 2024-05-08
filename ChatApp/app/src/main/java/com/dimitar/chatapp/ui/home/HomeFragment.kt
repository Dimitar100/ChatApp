package com.dimitar.chatapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dimitar.chatapp.R
import com.dimitar.chatapp.databinding.FragmentHomeBinding
import com.dimitar.chatapp.signin.SignInViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var jwt: String = ""
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        jwt = requireActivity().intent.extras!!.getString("JWT")!!
        val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(jwt) }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //jwt = requireActivity().intent.extras!!.getString("JWT")!!
        val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(jwt) }
        homeViewModel.getAllChats()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}