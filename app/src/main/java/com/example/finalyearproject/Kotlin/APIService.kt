package com.example.finalyearproject.Kotlin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    //Denotes that the request body will use form URL encoding.
    @FormUrlEncoded
    @POST("chat")
    fun chatBot(@Field("input") chatText : String ): Call<ChatResponse>
}

//Holds the data
data class ChatResponse(val reply: String)