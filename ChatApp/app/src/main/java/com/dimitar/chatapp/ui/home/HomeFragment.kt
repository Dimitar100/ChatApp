package com.dimitar.chatapp.ui.home

import android.app.Dialog
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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.chatapp.MainActivity
import com.dimitar.chatapp.R
import com.dimitar.chatapp.chat.ChatSocketServiceImpl
import com.dimitar.chatapp.databinding.*
import com.dimitar.chatapp.di.AppModule
import com.dimitar.chatapp.ui.signin.SignInViewModel
import com.dimitar.chatapp.util.User
import kotlinx.coroutines.launch

class HomeFragment : Fragment()  {

    //private lateinit var viewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private var jwt: String = ""
    private var username: String = ""
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
        username = requireActivity().intent.extras!!.getString("username")!!

        User.jwt = jwt
        User.username = username

        val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(jwt) }
        viewModel = homeViewModel

        val createChatDialog = Dialog(requireActivity())
        createChatDialog.setContentView(R.layout.create_chat_dialog)
        createChatDialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        createChatDialog.setCancelable(true)

        val addChatBtn: Button = requireView().findViewById(R.id.createChatBtn)
        addChatBtn.setOnClickListener{
            createChatDialog.show()
        }

        //Create Chat dialog elemts
        val chatName: TextView = createChatDialog.findViewById(R.id.editTextChatName)
        val chatParticipantName: TextView = createChatDialog.findViewById(R.id.editTextAddUser)
        val createChatBtn: Button = createChatDialog.findViewById(R.id.buttonCreate)

        createChatBtn.setOnClickListener{
            homeViewModel.createChat(chatName.text.toString(), chatParticipantName.text.toString())
        }


        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewChats)
        homeViewModel.getAllChats()
        val homeFragment = this
        lifecycleScope.launch {
            homeViewModel.uiState.collect {
                //Log.d("HOME_FRAGMENT", it.chats.toString())
                recyclerView.adapter = ChatAdapter(it.chats, homeFragment)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                //ChatSocketServiceImpl(AppModule.provideHttpClient()).initSession(username)
            }
        }




    }

    companion object {
        lateinit var viewModel: HomeViewModel
    }

    fun navigateToChat(){
        findNavController().navigate(R.id.navigation_chat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}