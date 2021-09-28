package com.example.calendar.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.R
import com.example.calendar.common.AppConsts
import com.example.calendar.databinding.ActivitySplashBinding
import com.example.calendar.datamodel.User
import com.example.calendar.screen.login.LogInActivity
import com.example.calendar.screen.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val auth = Firebase.auth

        binding.logo.alpha = 0f
        binding.logo.animate().setDuration(500).alpha(1f).withEndAction {

            // if not logged in go to log in otherwise go to main view
            if(auth.currentUser == null){
                startActivity(
                    Intent(this, LogInActivity::class.java)
                )
            }
            else {
                startActivity(
                    Intent(this, MainActivity::class.java).apply {
                        putExtra(AppConsts.USER, Gson().toJson(
                                User(auth.currentUser!!.uid)
                            )
                        )
                    }
                )
            }
            finish()
        }

    }
}