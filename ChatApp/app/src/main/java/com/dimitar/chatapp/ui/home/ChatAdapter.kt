package com.dimitar.chatapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.chatapp.R

class ChatAdapter(
    private val data: List<Chat>,
    private val homeFragment: HomeFragment
) : RecyclerView.Adapter<ChatAdapter.ItemViewHolder>() {

    //Setup variables to hold the instance of the views defined in your recyclerView item layout
    //Kinda like the onCreate method in an Activity
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textChatName)
        val creator: TextView = view.findViewById(R.id.textChatCreator)
        val chatId: TextView = view.findViewById(R.id.TextChatId)
    }

    //This is where you inflate the layout (Give each entry/row its look)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    // Set values to the views we pulled out of the recycler_view_row
    // layout file based on the position of the recyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val chat: Chat = data[position]

        holder.name.text = chat.chatName
        holder.creator.text = "Creator: ${chat.chatCreator}"
        holder.chatId.text = chat.chatId.toString()

        holder.itemView.setOnClickListener {
            //HomeFragment.onClick()
            homeFragment.findNavController().navigate(R.id.navigation_chat)
        }
    }

    //The recyclerView just wants to know how many items are currently in your dataset
    override fun getItemCount(): Int {
        return data.size
    }
}