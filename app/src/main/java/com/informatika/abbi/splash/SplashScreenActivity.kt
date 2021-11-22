package com.informatika.abbi.splash


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.informatika.abbi.MainActivity
import com.informatika.abbi.R
import com.informatika.abbi.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashScreenTimeOut = 4000
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, splashScreenTimeOut.toLong())
        overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation)
    }
}