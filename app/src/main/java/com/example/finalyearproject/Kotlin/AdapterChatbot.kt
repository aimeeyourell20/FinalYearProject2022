package com.example.finalyearproject.Kotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import kotlinx.android.synthetic.main.listitem_chat.view.*

class AdapterChatbot : RecyclerView.Adapter<AdapterChatbot.MyViewHolder>() {
    private val list = ArrayList<ChatModel>()

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_chat, parent, false)
    ) {
        fun bind(chat: ChatModel) = with(itemView) {
            if (!chat.isBot) {
                //To access txtchat components directly, we have to add kotlin plugin, now we can call components directly
                txtChat.setBackgroundColor(Color.GRAY)
                txtChat.setTextColor(Color.BLACK)
                txtChat.text = chat.chat
            } else {
                txtChat.setBackgroundColor(Color.TRANSPARENT)
                txtChat.setTextColor(Color.BLACK)
                txtChat.text = chat.chat
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addChatToList(chat: ChatModel) {
        list.add(chat)
        notifyDataSetChanged()
    }

}