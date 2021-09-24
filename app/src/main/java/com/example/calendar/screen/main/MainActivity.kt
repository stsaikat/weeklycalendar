package com.example.calendar.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.R
import com.example.calendar.common.AppConsts
import com.example.calendar.databinding.ActivityLogInBinding
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.datamodel.User
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = Gson().fromJson(
            intent.getStringExtra(AppConsts.USER),
            User::class.java
        )
    }
}