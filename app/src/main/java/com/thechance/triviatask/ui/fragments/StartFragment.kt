package com.thechance.triviatask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.thechance.triviatask.R
import com.thechance.triviatask.data.UrlModifier
import com.thechance.triviatask.databinding.FragmentStartBinding
import com.thechance.triviatask.util.Constant

class StartFragment : BaseFragment<FragmentStartBinding>() {
    private val bundle = Bundle()
    private var quizNumber = ""
    private val fragmentQuestion = QuestionFragment()
    override val LOG_TAG: String = "FragmentStartBinding"
    override val bindingInflater: (LayoutInflater) -> FragmentStartBinding = FragmentStartBinding::inflate
    override fun setup() {
        setSpinner()
    }
    override fun addCallBack() {
        onClickButton()
    }
    private fun onClickButton() {
        binding?.transToStart?.setOnClickListener {
            sendDataFunction()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, fragmentQuestion)?.commit()
        }
    }
    private fun setSpinner() {
        val items = UrlModifier.difficultyList
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding?.spinner?.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    private fun sendDataFunction() {
        var difficulty = "easy"

        binding?.apply {
            quizNumber = amountEditText.editText?.text.toString()
            categoryEditText.editText?.text.toString()
            setSpinner.setOnItemClickListener { _, _, i, _ ->
                difficulty = UrlModifier.difficultyList[i]
            }
            UrlModifier.getUrl(
                amountEditText.editText?.text.toString(),
                categoryEditText.editText?.text.toString(),
//                difficulty,
            )
        }
        val myArgument = bundle.apply {
            putString(Constant.KEY_QUESTION_NUMBER , quizNumber)
            Log.i("First_LOG",quizNumber)
        }
        fragmentQuestion.arguments = myArgument
    }

}
