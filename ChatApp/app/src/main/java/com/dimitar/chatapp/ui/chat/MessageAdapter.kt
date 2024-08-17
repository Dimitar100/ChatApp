package com.dimitar.chatapp.ui.chat

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ch.qos.logback.core.util.Loader.getResources
import com.dimitar.chatapp.R
import com.dimitar.chatapp.data.Message
import com.dimitar.chatapp.util.User


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

        val messageBubble: ConstraintLayout = view.findViewById(R.id.messageBubble)
        val messageRow: ConstraintLayout = view.findViewById(R.id.messageRow)


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


        if(User.username == message.senderId){
            holder.messageBubble.setBackgroundColor(ContextCompat.getColor(chatFragment.requireContext(), R.color.purple))
            val constraintSet = ConstraintSet()
            constraintSet.clone(holder.messageRow)
            constraintSet.clear(holder.messageBubble.id, ConstraintSet.LEFT)
            constraintSet.applyTo(holder.messageRow)


            holder.content.setTextColor(ContextCompat.getColor(chatFragment.requireContext(), R.color.white))
            holder.timestamp.setTextColor(ContextCompat.getColor(chatFragment.requireContext(), R.color.white))
            holder.senderId.setTextColor(ContextCompat.getColor(chatFragment.requireContext(), R.color.white))
        } else {
            val constraintSet = ConstraintSet()
            constraintSet.clone(holder.messageRow)
            constraintSet.clear(R.id.messageBubble, ConstraintSet.RIGHT)
            constraintSet.applyTo(holder.messageRow)
        }



    }

    //The recyclerView just wants to know how many items are currently in your dataset
    override fun getItemCount(): Int {
        return data.size
    }
}