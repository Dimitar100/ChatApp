package com.dimitar.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.chatapp.MainActivity
import com.dimitar.chatapp.R
import com.dimitar.chatapp.databinding.FragmentHomeBinding
import com.dimitar.chatapp.signin.SignInViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    //private lateinit var viewModel: HomeViewModel

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

//        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewChats)





        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //jwt = requireActivity().intent.extras!!.getString("JWT")!!
        jwt = requireActivity().intent.extras!!.getString("JWT")!!
        val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(jwt) }

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewChats)
        homeViewModel.getAllChats()
        lifecycleScope.launch {
            homeViewModel.uiState.collect {
                //Log.d("HOME_FRAGMENT", it.chats.toString())
                recyclerView.adapter = ChatAdapter(it.chats)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}