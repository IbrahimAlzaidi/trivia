package com.thechance.triviatask.data

import android.util.Log
import com.google.gson.Gson
import com.thechance.triviatask.util.model.TriviaQuestion
import com.thechance.triviatask.util.model.TriviaResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request

object Network {
    private val client = OkHttpClient()
    private const val baseUrl = "https://opentdb.com/api.php?amount=10&category=10&difficulty=easy&type=multiple"
    private val gson = Gson()
    fun makeRequestUsingOkhttp(): State<TriviaQuestion> {
        val request = Request.Builder().url(baseUrl).build()
        val response = client.newCall(request).execute()
        return if (response.isSuccessful){
            val responseQuiz = gson.fromJson(response.body.toString(), TriviaQuestion::class.java)
            Log.i("responseQuiz", responseQuiz.toString())
            State.Success(responseQuiz)
        }else{
            State.Error(response.message)
        }

    }
}
