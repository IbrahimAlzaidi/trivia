package com.thechance.triviatask

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thechance.triviatask.databinding.FragmentWinningBinding

class WinFragment:BaseFragment<FragmentWinningBinding>() {
    override val LOG_TAG: String
        get()= "win fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentWinningBinding = FragmentWinningBinding::inflate


    override fun setup() {
        Log.v(LOG_TAG, arguments?.getInt("points").toString())
        val points = arguments?.getInt("points")
        binding?.textPoints?.text = points.toString()

        binding?.buttonSong?.setOnClickListener{
            val url = "https://www.youtube.com/watch?v=0aUav1lx3rA"
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }



    override fun addCallBack() {
    }
}