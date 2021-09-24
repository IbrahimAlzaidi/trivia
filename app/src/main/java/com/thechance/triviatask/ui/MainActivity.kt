package com.thechance.triviatask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thechance.triviatask.R
import com.thechance.triviatask.databinding.ActivityMainBinding
import com.thechance.triviatask.ui.fragments.QuestionFragment
import com.thechance.triviatask.ui.fragments.StartFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    private  val fragmentStart = StartFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.container,fragmentStart).commit()
    }
}
