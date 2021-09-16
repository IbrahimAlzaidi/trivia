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
    var points:Int = 0
    override val LOG_TAG: String
        get()= "win fragment"

    override val bindingInflater: (LayoutInflater) -> FragmentWinningBinding = FragmentWinningBinding::inflate


    override fun setup() {
        points = requireArguments().getInt(Constatnt.POINTS)

        //println("FROM FRAGMENT : $points")
        showResult()
        binding?.buttonSong?.setOnClickListener{
           displaySong()
        }
        binding?.buttonResetGame?.setOnClickListener {
            restartGame()
        }
    }

    private fun restartGame() {
        val questionFragment = QuestionFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,questionFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun displaySong() {
        val url = "https://www.youtube.com/watch?v=0aUav1lx3rA"
        val intent= Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun showResult() {
        if (points > 5){
            binding?.textResult?.text = Constatnt.WIN_TEXT
            binding?.textPoints?.text = points.toString()
        } else{
            binding?.textResult?.text =Constatnt.LOSE_TEXT
            binding?.textPoints?.text = points.toString()
        }
        }


    override fun addCallBack() {
    }
}