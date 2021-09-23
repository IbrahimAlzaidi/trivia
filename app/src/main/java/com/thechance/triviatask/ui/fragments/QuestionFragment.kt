package com.thechance.triviatask.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.thechance.triviatask.util.Constatnt
import com.thechance.triviatask.R
import com.thechance.triviatask.data.Data
import com.thechance.triviatask.data.State
import com.thechance.triviatask.util.model.TriviaQuestion
import com.thechance.triviatask.databinding.FragmentQuestionsBinding
import com.thechance.triviatask.util.model.TriviaResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException


class QuestionFragment: BaseFragment<FragmentQuestionsBinding>() {
    private val disposable: CompositeDisposable = CompositeDisposable()

    private val client = OkHttpClient()
    var index:Int = 0
    var point:Int = 0
    var correctAnswer = ""
    var answerQuestion = mutableListOf<String?>()
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
        if (index>=10) displayWinFragment()else {

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
                        val info = homeInfo.itemTypes?.toMutableList()?.get(index)
                         answerQuestion = mutableListOf(
                            info?.incorrectAnswers?.get(0),
                            info?.incorrectAnswers?.get(1),
                            info?.incorrectAnswers?.get(2),
                            info?.correctAnswer
                        ).shuffled().toMutableList()
                        correctAnswer = info?.correctAnswer.toString()
                        activity?.runOnUiThread {
                            binding?.textQuestion?.text = info?.question
                            binding?.textFirstAnswer?.text = answerQuestion[0]
                            binding?.textSecondAnswer?.text = answerQuestion[1]
                            binding?.textThirdAnswer?.text = answerQuestion[2]
                            binding?.textFourthAnswer?.text = answerQuestion[3]
                            binding?.textPoints?.text = index.toString()

                            //println(index)
                            isEnabledButton(value = false)
                        }
                        index++
                        println(info?.correctAnswer!!)
                    }



                }

            })
            //end of (if-else) statement
        }
    }
    private fun displayWinFragment() {
        val winFragment = WinFragment()
        val bundle= Bundle()
        bundle.putInt(Constatnt.POINTS,point)
        winFragment.arguments = bundle
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
            countPoints(binding?.textFirstAnswer!!)
        }
        binding?.textSecondAnswer?.setOnClickListener {
            getAnswer(binding?.textSecondAnswer!!)
            countPoints(binding?.textSecondAnswer!!)

        }
        binding?.textThirdAnswer?.setOnClickListener {
            getAnswer(binding?.textThirdAnswer!!)
            countPoints(binding?.textThirdAnswer!!)

        }
        binding?.textFourthAnswer?.setOnClickListener {
            getAnswer(binding?.textFourthAnswer!!)
            countPoints(binding?.textFourthAnswer!!)

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
    private fun countPoints(answerText:TextView){
        if (answerText.text == correctAnswer)
            point++
        println("POINTS: $point")

    }




    /////////////////////////////////
    //////////////////////////////
    /////////////////////////////
    ///////////////////////////
    /////////////////////////
    //////////////////////
    ///////////////////
    ////////////////
    ///////////////
    /////////////////
    ///////////////////
    //////////////////////
    ///////////////////////////
    /////////////////////////////
    ///////////////////////////////
    /////////////////////////////////
//  Data.getResultForQuiz().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()

    private fun getResultForQuiz(){
        disposable.add(
            Data.getResultForQuiz()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    } // emitter the Data*
    private fun onQuizResult(response: State<TriviaResult>){
        when(response){
            is State.Error -> {
                binding?.imageError?.show()//here View is adding for show image , so i hope u can edit it
            }
            is State.Loading -> {
                binding?.progressLoading?.show()//here View is adding for show progress bar
            }
            is State.Success -> {
                bindData(response.data)
            }
        }
    }//show think depending on the state>
    private fun View.show() {
        this.visibility = View.VISIBLE
    }//show  the progress bar and Image>
    private fun View.hide() {
        this.visibility = View.GONE
    }//hide the progress bar and Image>
    private fun hideAllViews(){
        binding?.imageError?.hide()
        binding?.progressLoading?.hide()
        binding?.textMaxTemp?.hide()
    }
    private fun bindData(data: TriviaResult){
        binding?.textQuestion?.text = data.question
        binding?.textFirstAnswer?.text = answerQuestion[0]
        binding?.textSecondAnswer?.text = answerQuestion[1]
        binding?.textThirdAnswer?.text = answerQuestion[2]
        binding?.textFourthAnswer?.text = answerQuestion[3]
    }//bind Data for Views*
    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }//to Destroy the Disposable Variable



}
