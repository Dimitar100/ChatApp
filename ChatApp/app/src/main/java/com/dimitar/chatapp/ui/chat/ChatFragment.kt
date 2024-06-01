package com.dimitar.chatapp.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.dimitar.chatapp.R
import com.dimitar.chatapp.chat.ChatSocketServiceImpl
import com.dimitar.chatapp.di.AppModule
import com.dimitar.chatapp.util.CurrentChat
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sendBtn: Button = requireView().findViewById(R.id.sendBtn)
        val test : TextView = requireView().findViewById(R.id.test)

        sendBtn.setOnClickListener{
            test.text = CurrentChat.Id.toString()
            //viewModel.sendMsg(CurrentChat.Id, "HELLO THERE!")

            lifecycleScope.launch {

                CurrentChat.chatSocketService?.sendMessage("HELLO THERE!", CurrentChat.Id)
            }
        }

        lifecycleScope.launch {

            var messages = CurrentChat.chatSocketService?.observeMessages()
            messages!!.collect {
                Log.d("INCOMING_FRAGMENT", it.content)
            }
        }

    }

}