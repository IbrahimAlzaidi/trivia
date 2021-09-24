package com.thechance.triviatask.ui.fragments

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import com.thechance.triviatask.R
import com.thechance.triviatask.databinding.FragmentWinningBinding
import com.thechance.triviatask.util.Constant

class WinFragment: BaseFragment<FragmentWinningBinding>() {
    var points:Int = 0
    override val LOG_TAG: String
        get()= "win fragment"

    override val bindingInflater: (LayoutInflater) -> FragmentWinningBinding = FragmentWinningBinding::inflate


    override fun setup() {
        points = requireArguments().getInt(Constant.POINTS)

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
        val startFragment = StartFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,startFragment)
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
            binding?.textResult?.text = Constant.WIN_TEXT
            binding?.textPoints?.text = points.toString()
        } else{
            binding?.textResult?.text = Constant.LOSE_TEXT
            binding?.textPoints?.text = points.toString()
        }
        }


    override fun addCallBack() {
    }
}
