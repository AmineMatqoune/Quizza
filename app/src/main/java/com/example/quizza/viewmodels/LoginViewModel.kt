package com.example.quizza.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel: ViewModel() {

    companion object{
        var isLogged = MutableLiveData<Boolean>()

        fun loginSuccess(){
            isLogged.postValue(true)
        }

        fun loginFail(){
            isLogged.postValue(false)
        }

        fun getStatus(): Boolean? {
            return isLogged.value
        }
    }
}