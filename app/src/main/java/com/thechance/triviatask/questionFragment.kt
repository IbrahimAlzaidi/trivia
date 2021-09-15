package com.thechance.triviatask

import android.view.LayoutInflater
import com.thechance.triviatask.databinding.FragmentQuestionsBinding


class questionFragment:BaseFragment<FragmentQuestionsBinding>() {
    override val LOG_TAG: String
        get() = javaClass.simpleName
    override val bindingInflater: (LayoutInflater) -> FragmentQuestionsBinding = FragmentQuestionsBinding::inflate

    override fun setup() {
        TODO("Not yet implemented")
    }

    override fun addCallBack() {
        TODO("Not yet implemented")
    }


}