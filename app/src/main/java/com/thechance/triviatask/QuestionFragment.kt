package com.thechance.triviatask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.TextView
import com.google.gson.Gson
import com.thechance.triviatask.databinding.FragmentQuestionsBinding
import okhttp3.*
import java.io.IOException


class QuestionFragment:BaseFragment<FragmentQuestionsBinding>() {
    private val client = OkHttpClient()
    var index:Int = 0
    private val winFragment = WinFragment()
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
        if (index>=10) setFragment()else {

            val url =
                "https://opentdb.com/api.php?amount=10&category=10&difficulty=easy&type=multiple"
            val request = Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("FAILED , ${e.message.toString()}")
                }


                override fun onResponse(call: Call, response: Response) {
                    response.body?.string().let { jsonString ->
                        val homeInfo = Gson().fromJson(jsonString, TriviaQuestion::class.java)
                        val info = homeInfo.results?.toMutableList()?.get(index)
                        var answerQuestion = mutableListOf(
                            info?.incorrect_answers?.get(0),
                            info?.incorrect_answers?.get(1),
                            info?.incorrect_answers?.get(2),
                            info?.correct_answer
                        ).shuffled().toMutableList()
                        activity?.runOnUiThread {
                            binding?.textQuestion?.text = info?.question
                            binding?.textFirstAnswer?.text = answerQuestion[0]
                            binding?.textSecondAnswer?.text = answerQuestion[1]
                            binding?.textThirdAnswer?.text = answerQuestion[2]
                            binding?.textFourthAnswer?.text = answerQuestion[3]
                            binding?.textPoints?.text = index.toString()

                            println(index)
                            isEnabledButton(value = false)
                        }
                        index++
                    }



                }

            })
            //end of (if-else) statement
        }
    }
    private fun setFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container,winFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isEnabledButton(value:Boolean){
        when(value){
            false-> binding?.buttonNext?.isEnabled = false
            true -> binding?.buttonNext?.isEnabled = true
        }
    }

    private fun getNextQuestion() {
        binding?.answerLinearLayout?.setOnClickListener {
            isEnabledButton(true)
        }
        binding?.buttonNext?.setOnClickListener {
            showInfo()
            getDefaultStyle()
        }
    }

    @SuppressLint("ResourceType")
    private fun getCorrectAnswer() {
        binding?.textFirstAnswer?.setOnClickListener {
            getAnswer(binding?.textFirstAnswer!!)
        }
        binding?.textSecondAnswer?.setOnClickListener {
            getAnswer(binding?.textSecondAnswer!!)
        }
        binding?.textThirdAnswer?.setOnClickListener {
            getAnswer(binding?.textThirdAnswer!!)
        }
        binding?.textFourthAnswer?.setOnClickListener {
            getAnswer(binding?.textFourthAnswer!!)
        }
    }

        private fun getAnswer(textView: TextView) {
            when(textView){
                binding?.textFirstAnswer -> binding?.textFirstAnswer!!.setBackgroundResource(R.drawable.correct_answer)
                binding?.textSecondAnswer -> binding?.textSecondAnswer!!.setBackgroundResource(R.drawable.correct_answer)
                binding?.textThirdAnswer -> binding?.textThirdAnswer!!.setBackgroundResource(R.drawable.correct_answer)
                binding?.textFourthAnswer -> binding?.textFourthAnswer!!.setBackgroundResource(R.drawable.correct_answer)
            }
            isEnabledButton(true)
        }

    private fun getDefaultStyle(){
        binding?.textFirstAnswer?.setBackgroundResource(R.drawable.text_background)
        binding?.textSecondAnswer?.setBackgroundResource(R.drawable.text_background)
        binding?.textThirdAnswer?.setBackgroundResource(R.drawable.text_background)
        binding?.textFourthAnswer?.setBackgroundResource(R.drawable.text_background)
    }



}