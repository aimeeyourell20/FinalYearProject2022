package com.example.finalyearproject.Kotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import kotlinx.android.synthetic.main.listitem_chat.view.*

class AdapterChatbot : RecyclerView.Adapter<AdapterChatbot.MyViewHolder>() {
    private val list = ArrayList<Chat>()

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_chat, parent, false)
    ) {
        fun bind(chat: Chat) = with(itemView) {
            if (!chat.isBot) {
                chatTxt.text = chat.chat
            } else {
                chatTxt.setBackgroundResource(R.drawable.boarders_blue)
                chatTxt.setTextColor(Color.BLACK)
                chatTxt.setPadding(18)
                chatTxt.text = chat.chat
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addReply(chat: Chat) {
        list.add(chat)
        notifyDataSetChanged()
    }

}
