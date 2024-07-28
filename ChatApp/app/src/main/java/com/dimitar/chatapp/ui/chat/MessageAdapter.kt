package com.dimitar.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.chatapp.R
import com.dimitar.chatapp.data.Message


class MessageAdapter(
    private val data: List<Message>,
    private val chatFragment: ChatFragment
) : RecyclerView.Adapter<MessageAdapter.ItemViewHolder>() {

    //Setup variables to hold the instance of the views defined in your recyclerView item layout
    //Kinda like the onCreate method in an Activity
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.messageContent)
        val timestamp: TextView = view.findViewById(R.id.timestamp)
        val senderId: TextView = view.findViewById(R.id.sender)
    }

    //This is where you inflate the layout (Give each entry/row its look)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_recycler_view_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    // Set values to the views we pulled out of the recycler_view_row
    // layout file based on the position of the recyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val message: Message = data[position]

        holder.content.text = message.content
        holder.timestamp.text = message.formattedTime//"Creator: ${chat.chatCreator}"
        holder.senderId.text = message.senderId

        /*holder.itemView.setOnClickListener {
            //HomeFragment.onClick()
            CurrentChat.Id = holder.chatId.text.toString().toInt()
            homeFragment.findNavController().navigate(R.id.navigation_chat)
        }*/
    }

    //The recyclerView just wants to know how many items are currently in your dataset
    override fun getItemCount(): Int {
        return data.size
    }
}