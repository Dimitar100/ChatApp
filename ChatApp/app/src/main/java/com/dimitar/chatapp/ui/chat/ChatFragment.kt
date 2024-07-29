package com.dimitar.chatapp.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.chatapp.R
import com.dimitar.chatapp.chat.ChatSocketServiceImpl
import com.dimitar.chatapp.di.AppModule
import com.dimitar.chatapp.ui.home.ChatAdapter
import com.dimitar.chatapp.ui.home.HomeViewModel
import com.dimitar.chatapp.ui.home.HomeViewModelFactory
import com.dimitar.chatapp.util.CurrentChat
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

   // private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
      //  viewModel.getMessages()
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sendBtn: Button = requireView().findViewById(R.id.sendBtn)
        val test : TextView = requireView().findViewById(R.id.test)
        val editTextField: EditText = requireView().findViewById(R.id.editTextMessage)

        val chatViewModel: ChatViewModel by viewModels { ChatViewModelFactory() }

        sendBtn.setOnClickListener{
            test.text = CurrentChat.Id.toString()
            //viewModel.sendMsg(CurrentChat.Id, "HELLO THERE!")

            lifecycleScope.launch {
                CurrentChat.chatSocketService?.sendMessage(editTextField.text.toString(), CurrentChat.Id)
                editTextField.setText("")
            }
        }

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewMessages)
        chatViewModel.getMessages()
        val chatFragment = this
        lifecycleScope.launch {
            chatViewModel.uiState.collect {
                recyclerView.adapter = MessageAdapter(it.messages, chatFragment)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                recyclerView.scrollToPosition(recyclerView.adapter!!.itemCount-1)

            }
        }
        lifecycleScope.launch {
            var messages = CurrentChat.chatSocketService?.observeMessages()
            messages!!.collect {
                Log.d("INCOMING_FRAGMENT", it.content)
                chatViewModel.getMessages()
            }
        }
    }
}