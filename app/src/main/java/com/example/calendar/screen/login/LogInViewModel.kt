package com.example.calendar.screen.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.calendar.datamodel.User

class LogInViewModel(private val app: Application) : AndroidViewModel(app) {

    val user: MutableLiveData<User?> = MutableLiveData(null)
    val toastMessage: MutableLiveData<String?> = MutableLiveData(null)

    fun logIn(username: String?, password: String?){
        if(
            !validateUserName(username) ||
            !validatePassword(password)
        ){
            toastMessage.postValue("Please enter valid username and password")
            return
        }
        // TODO: 24/09/2021  
    }

    fun signUp(username: String?,password: String?){


        if(!validateUserName(username)){
            toastMessage.postValue("Please Enter valid username")
            return
        }
        if(!validatePassword(password)){
            toastMessage.postValue("Please Enter valid password(min 6 char)")
            return
        }

        // TODO: 24/09/2021  
    }
    
    private fun validateUserName(username: String?) : Boolean {
        if (username == null) return false
        username.forEach { 
            if(!((it in 'a'..'z') || (it in '0'..'9'))) return false
        }
        return true
    }
    
    private fun validatePassword(password: String?) : Boolean{
        if(password == null) return false
        if(password.length < 6) return false
        return true
    }
}