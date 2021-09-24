package com.thechance.triviatask.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.thechance.triviatask.R
import com.thechance.triviatask.data.Data
import com.thechance.triviatask.data.State
import com.thechance.triviatask.databinding.FragmentQuestionsBinding
import com.thechance.triviatask.util.Constatnt
import com.thechance.triviatask.util.model.TriviaQuestion
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class QuestionFragment : BaseFragment<FragmentQuestionsBinding>() {
    private val disposable: CompositeDisposable = CompositeDisposable()

    private var index: Int = 0
    private var point: Int = 0
    private var correctAnswer = ""
    override val LOG_TAG: String
        get() = javaClass.simpleName
    override val bindingInflater: (LayoutInflater) -> FragmentQuestionsBinding =
        FragmentQuestionsBinding::inflate

    override fun setup() {
//        showInfo()
        getResultForQuiz()
        getNextQuestion()
        getCorrectAnswer()
    }

    override fun addCallBack() {
    }

    private fun displayWinFragment() {
        val winFragment = WinFragment()
        val bundle = Bundle()
        bundle.putInt(Constatnt.POINTS, point)
        winFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, winFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isEnabledButton(value: Boolean) {
        when (value) {
            false -> binding?.buttonNext?.isEnabled = false
            true -> binding?.buttonNext?.isEnabled = true
        }
    }

    private fun getNextQuestion() {
        binding?.answerLinearLayout?.setOnClickListener {
            isEnabledButton(true)
        }
        binding?.buttonNext?.setOnClickListener {
            getResultForQuiz()
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
        when (textView) {
            binding?.textFirstAnswer -> binding?.textFirstAnswer!!.setBackgroundResource(R.drawable.correct_answer)
            binding?.textSecondAnswer -> binding?.textSecondAnswer!!.setBackgroundResource(R.drawable.correct_answer)
            binding?.textThirdAnswer -> binding?.textThirdAnswer!!.setBackgroundResource(R.drawable.correct_answer)
            binding?.textFourthAnswer -> binding?.textFourthAnswer!!.setBackgroundResource(R.drawable.correct_answer)
        }
        isEnabledButton(true)
    }

    private fun getDefaultStyle() {
        binding?.textFirstAnswer?.setBackgroundResource(R.drawable.text_background)
        binding?.textSecondAnswer?.setBackgroundResource(R.drawable.text_background)
        binding?.textThirdAnswer?.setBackgroundResource(R.drawable.text_background)
        binding?.textFourthAnswer?.setBackgroundResource(R.drawable.text_background)
    }

    private fun countPoints(answerText: TextView) {
        if (answerText.text == correctAnswer)
            point++
        println("POINTS: $point")

    }

    private fun getResultForQuiz() {
        disposable.add(
            Data.getResultForQuiz()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onQuizResult)
        )
    } // emitter the Data*

    private fun onQuizResult(response: State<TriviaQuestion>) {
        hideAllViews()
        when (response) {
            is State.Error -> {
                binding?.imageError?.show()//here View is adding for show image , so i hope u can edit it
            }
            is State.Loading -> {
                binding?.progressLoading?.smoothToShow()//here View is adding for show progress bar
            }
            is State.Success -> {
                bindData(response.data)
            }
        }
    }//show thinks depending on the state>

    private fun View.show() {
        this.visibility = View.VISIBLE
    }//show  the progress bar and Image>

    private fun View.hide() {
        this.visibility = View.GONE
    }//hide the progress bar and Image>

    private fun hideAllViews() {
        binding?.imageError?.hide()
        binding?.progressLoading?.smoothToHide()
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(data: TriviaQuestion) {
        if (index >= 10)
        {displayWinFragment()}
        else {
            val  answerQuestion = mutableListOf(data.itemTypes?.get(index)?.correctAnswer.toString(),
                data.itemTypes?.get(index)?.incorrectAnswers?.get(0),
                data.itemTypes?.get(index)?.incorrectAnswers?.get(1),
                data.itemTypes?.get(index)?.incorrectAnswers?.get(2)
            ).shuffled().toMutableList()
            binding?.textQuestion?.text = data.itemTypes?.get(index)?.question
            binding?.textFirstAnswer?.text = answerQuestion[0].toString()
            binding?.textSecondAnswer?.text = answerQuestion[1].toString()
            binding?.textThirdAnswer?.text = answerQuestion[2].toString()
            binding?.textFourthAnswer?.text = answerQuestion[3].toString()
            binding?.textPoints?.text = (index).toString()
            //println(data.itemTypes?.get(index)?.correctAnswer.toString())
            isEnabledButton(true)
            getCorrectAnswer()
        }
        index++
    }//bind Data for Views*

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }//to Destroy the Disposable Variable
}
