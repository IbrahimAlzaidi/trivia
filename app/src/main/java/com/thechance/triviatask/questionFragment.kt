package com.thechance.triviatask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.google.gson.Gson
import com.thechance.triviatask.databinding.FragmentQuestionsBinding
import okhttp3.*
import java.io.IOException


class questionFragment:BaseFragment<FragmentQuestionsBinding>() {
    val client = OkHttpClient()
    override val LOG_TAG: String
        get() = javaClass.simpleName
    override val bindingInflater: (LayoutInflater) -> FragmentQuestionsBinding = FragmentQuestionsBinding::inflate

    override fun setup() {
        showInfo()
        getNextQuestion()
        getCorrectAnswer()
    }

    override fun addCallBack() {
    }


    private fun showInfo() {
            val url = "https://opentdb.com/api.php?amount=10&category=10&difficulty=easy&type=multiple"
            val request = Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("FAILED , ${e.message.toString()}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string().let { jsonString ->
                        val homeInfo = Gson().fromJson(jsonString, TriviaQuestion::class.java)
                        val info = homeInfo.results?.toMutableList()?.get(0)
                        activity?.runOnUiThread(Runnable {
                            binding?.textQuestion?.text = info?.question
                            binding?.textFirstAnswer?.text = info?.correct_answer
                            binding?.textSecondAnswer?.text = info?.incorrect_answers?.get(0)
                            binding?.textThirdAnswer?.text = info?.incorrect_answers?.get(1)
                            binding?.textFourthAnswer?.text = info?.incorrect_answers?.get(2)
                            isEnabledButton(false)

                        })
                    }



                }

            })
    }
    private fun isEnabledButton(value:Boolean){
        when(value){
            false-> binding?.buttonNext?.isEnabled = false
            true -> binding?.buttonNext?.isEnabled = true
        }
    }

    private fun getNextQuestion() {

        binding?.buttonNext?.setOnClickListener {
            showInfo()
            getDefaultStyle()
        }
    }

    @SuppressLint("ResourceType")
    private fun getCorrectAnswer() {
        binding?.textFirstAnswer?.setOnClickListener {
            getAnswer()
        }
        binding?.textSecondAnswer?.setOnClickListener {
            getAnswer()
        }
        binding?.textThirdAnswer?.setOnClickListener {
            getAnswer()
        }
        binding?.textFourthAnswer?.setOnClickListener {
            getAnswer()
        }
    }

        private fun getAnswer() {
            binding?.textFirstAnswer?.setBackgroundResource(R.drawable.correct_answer)
            binding?.textSecondAnswer?.setBackgroundResource(R.drawable.wrong_answer)
            binding?.textThirdAnswer?.setBackgroundResource(R.drawable.wrong_answer)
            binding?.textFourthAnswer?.setBackgroundResource(R.drawable.wrong_answer)
            isEnabledButton(true)



        }
        private fun getDefaultStyle(){
            binding?.textFirstAnswer?.setBackgroundResource(R.drawable.text_background)
            binding?.textSecondAnswer?.setBackgroundResource(R.drawable.text_background)
            binding?.textThirdAnswer?.setBackgroundResource(R.drawable.text_background)
            binding?.textFourthAnswer?.setBackgroundResource(R.drawable.text_background)
            isEnabledButton(true)

        }


}