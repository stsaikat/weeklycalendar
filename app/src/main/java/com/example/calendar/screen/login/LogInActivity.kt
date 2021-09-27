package com.example.calendar.screen.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.calendar.R
import com.example.calendar.common.AppConsts
import com.example.calendar.databinding.ActivityLogInBinding
import com.example.calendar.datamodel.User
import com.example.calendar.screen.main.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class LogInActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLogInBinding
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var mToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        // auto log in
        Firebase.auth.currentUser?.let {
            viewModel.user.postValue(
                User(it.uid)
            )
        }
        // -----------*/
        
        viewModel.toastMessage.observe(this,{
            showResultView()
            it?.let { 
                viewModel.toastMessage.postValue(null)
                showToast(it)
            }
        })

        viewModel.dialogText.observe(this,{
            showResultView()
            it?.let {
                viewModel.dialogText.postValue(null)
                MaterialAlertDialogBuilder(this)
                    .setTitle("Help Text")
                    .setMessage("$it")
                    .setPositiveButton("ok",null)
                    .show()
            }
        })
        
        viewModel.user.observe(this,{
            showResultView()
            it?.let { user ->
                startActivity(
                    Intent(this,MainActivity::class.java).apply {
                        putExtra(AppConsts.USER, Gson().toJson(user))
                    }
                )
                finish()
            }
        })
    }

    private fun showLoadingView(){
        binding.pbLoadingUser.visibility = View.VISIBLE
        binding.btSignUp.isEnabled = false
        binding.btLogIn.isEnabled = false
    }

    private fun showResultView(){
        binding.pbLoadingUser.visibility = View.GONE
        binding.btSignUp.isEnabled = true
        binding.btLogIn.isEnabled = true
    }

    fun onSignUpClick(view: View){
        showLoadingView()
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.signUp(username,password)
    }

    fun onLogInClick(view: View){
        showLoadingView()
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