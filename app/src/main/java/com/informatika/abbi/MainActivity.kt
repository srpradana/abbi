package com.informatika.abbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.informatika.abbi.belajar.BelajarActivity
import com.informatika.abbi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvBelajar.setOnClickListener{
            val intent = Intent(this@MainActivity, BelajarActivity::class.java)
            startActivity(intent)
        }
    }
}