package com.thechance.triviatask.data

import android.util.Log
import com.google.gson.Gson
import com.thechance.triviatask.util.model.TriviaQuestion
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {
    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    private val gson = Gson()
    fun makeRequestUsingOkhttp(): State<TriviaQuestion> {
        val request = Request.Builder().url(UrlModifier.url).build()
        Log.i("MY_URL", UrlModifier.url)
        val response = client.newCall(request).execute()
        return if (response.isSuccessful){
            val responseQuiz = gson.fromJson(response.body?.string(), TriviaQuestion::class.java)
            Log.i("responseQuiz", responseQuiz.itemTypes.toString())
            State.Success(responseQuiz)
        }else{
            State.Error(response.message)
        }

    }
}
