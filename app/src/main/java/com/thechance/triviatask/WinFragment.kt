package com.thechance.triviatask

import android.view.LayoutInflater
import com.thechance.triviatask.databinding.FragmentWinningBinding


class WinFragment:BaseFragment<FragmentWinningBinding>() {
    override val LOG_TAG: String
        get()= "win fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentWinningBinding = FragmentWinningBinding::inflate
    override fun setup() {

    }

    override fun addCallBack() {
    }
}