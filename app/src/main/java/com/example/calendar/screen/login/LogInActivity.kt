package com.example.calendar.screen.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.calendar.R
import com.example.calendar.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLogInBinding
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var mToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel.toastMessage.observe(this,{
            it?.let { 
                viewModel.toastMessage.postValue(null)
                showToast(it)
            }
        })
        
        viewModel.user.observe(this,{
            it?.let {
                // TODO: 24/09/2021  
            }
        })
    }

    fun onSignUpClick(view: View){
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.signUp(username,password)
    }

    fun onLogInClick(view: View){
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.logIn(username,password)
    }

    private fun showToast(message: String){
        if(this::mToast.isInitialized) mToast.cancel()
        mToast = Toast.makeText(this,message,Toast.LENGTH_LONG)
        mToast.show()
    }

    fun onToggleClick(view: View){
        if(binding.btLogIn.visibility == View.VISIBLE){
            binding.btLogIn.visibility = View.GONE
            binding.btSignUp.visibility = View.VISIBLE
            binding.btCreateAccount.text = "Go To Log In"
        }
        else{
            binding.btLogIn.visibility = View.VISIBLE
            binding.btSignUp.visibility = View.GONE
            binding.btCreateAccount.text = "Create New Account"
        }
    }
}