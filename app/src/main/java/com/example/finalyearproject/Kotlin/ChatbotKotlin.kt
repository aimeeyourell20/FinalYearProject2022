package com.example.finalyearproject.Kotlin
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalyearproject.R
import kotlinx.android.synthetic.main.chatbotkotlin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//This Class is used to prepare retrofit to make the call.
class ChatbotKotlin : AppCompatActivity() {
    private val ChatBotAdapter = AdapterChatbot()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbotkotlin)

        //We create a retrofit builder object that will contain the base url of the api,the converter Gson Converter
        val retrofit = Retrofit.Builder()
                .baseUrl("http://172.20.10.3:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(APIService::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChatBotAdapter


        //Press to send the message to the chatbot
        sendButton.setOnClickListener {
            //If text area is blank user will be notified to enter text
            if(chat.text.isNullOrEmpty()){
                Toast.makeText(this@ChatbotKotlin, "Please enter a text", Toast.LENGTH_LONG).show()
                //Helps to link a listener with certain attributes
                return@setOnClickListener
            }

            ChatBotAdapter.addReply(Chat(chat.text.toString()))
            service.chatBot(chat.text.toString()).enqueue(response)
            chat.text.clear()
        }
    }

    private val response = object  : Callback<ChatResponse>{
        override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
            //If successful then it will display an appropriate message to the user
            if(response.isSuccessful &&  response.body()!= null){
                ChatBotAdapter.addReply(Chat(response.body()!!.reply, true))
            }else{
                //If unsuccessful user will be notified
                Toast.makeText(this@ChatbotKotlin, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
        //Errors if unable to connect to API
        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
            Toast.makeText(this@ChatbotKotlin, "Error", Toast.LENGTH_LONG).show()
        }

    }
}