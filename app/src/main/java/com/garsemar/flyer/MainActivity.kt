package com.garsemar.flyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.garsemar.flyer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Flyer)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // User: itb, pass: superultrasegurapassword
    }
}