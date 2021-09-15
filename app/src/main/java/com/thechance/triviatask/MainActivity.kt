package com.thechance.triviatask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thechance.triviatask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    private  val fragmentQuestion = questionFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.container,fragmentQuestion).commit()
    }
}