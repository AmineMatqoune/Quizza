package com.example.quizza.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LogRegViewModel: ViewModel() {

    companion object{
        var isLogged = MutableLiveData<Boolean>()
        var isRegistered = MutableLiveData<Boolean>()

        fun loginSuccess(){
            isLogged.postValue(true)
        }

        fun loginFail(){
            isLogged.postValue(false)
        }

        fun getStatus(): Boolean? {
            return isLogged.value
        }

        fun registerFail() {
            isRegistered.postValue(false)
        }

        fun registerSuccess() {
            isRegistered.postValue(true)
        }
    }
}