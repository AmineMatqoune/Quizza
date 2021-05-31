package com.example.quizza

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.login_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.quizza.viewmodels.LoginViewModel

class LoginDialog(myContext: Context): DialogFragment() {

    private val appContext = myContext
    private lateinit var userBundle: Bundle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.login_dialog, container, false)

        rootView.btnLogin.setOnClickListener{
            //Validate input and then try log-in
            if(validateInput(rootView.etUsernameLog.text.toString(), rootView.etpPasswordLog.text.toString())) {
                val db = DBManager.getInstance(appContext)
                val userDao = db.userDAO()

                CoroutineScope(Dispatchers.IO).launch {
                    val user = userDao.checkLogin(rootView.etUsernameLog.text.toString(), rootView.etpPasswordLog.text.toString())

                    if(user == null)                  //Equivalent of "Invalid Credentials"
                        LoginViewModel.loginFail()
                    else {                            //create user bundle to pass to homepage activity
                        userBundle = Bundle()
                        userBundle.putString("username", user.username)
                        userBundle.putString("avatar", user.avatar)
                        userBundle.putInt("totalScore", user.totalScore)
                        LoginViewModel.loginSuccess()
                    }
                }
            }
            else
                Toast.makeText(appContext, getString(R.string.wrong_credentials_login_dialog), Toast.LENGTH_SHORT).show()
        }

        setViewModel()
        return rootView
    }

    private fun validateInput(username: String, password: String): Boolean {
        //assuming that both username and password must be no longer than 18 char and no shorter than 5 char
        return username.length < 19 && username.length > 4 && password.length > 4 && password.length < 19
    }

    private fun setViewModel(){
        val loginObserver = Observer<Boolean>{ value ->
            if(LoginViewModel.getStatus() == false)
                Toast.makeText(appContext, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            else{
                Toast.makeText(appContext, "Login Succesfull", Toast.LENGTH_SHORT).show()
                val intentHomePage = Intent(appContext, Homepage::class.java)
                intentHomePage.putExtras(userBundle)  //passing the bundle into the intent
                startActivity(intentHomePage)
            }
        }

        LoginViewModel.isLogged.observe(this, loginObserver)
    }

}