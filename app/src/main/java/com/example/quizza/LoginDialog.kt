package com.example.quizza

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.login_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginDialog(myContext: Context): DialogFragment() {

    private val appContext = myContext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.login_dialog, container, false)

        rootView.btnLogin.setOnClickListener{
            //Validate input and then try log-in
            if(validateInput(rootView.etUsernameLog.text.toString(), rootView.etpPasswordLog.text.toString())) {
                val db = DBManager.getInstance(appContext)
                val userDao = db.userDAO()

                CoroutineScope(Dispatchers.IO).launch {
                    val user = userDao.checkLogin(rootView.etUsernameLog.text.toString(), rootView.etpPasswordLog.text.toString())

                    if(user == null)
                        println("Invalid credentials")
                    else
                        println("LLLLLLLOOOOOOOOOGGGGGIIIIIINNNNNNN" + user.totalScore)
                }
            }
            else
                Toast.makeText(appContext, rootView.btnLogin.text, Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

    private fun validateInput(username: String, password: String): Boolean {
        //assuming that both username and password must be no longer than 18 char and no shorter than 5 char
        return username.length < 19 && username.length > 4 && password.length > 4 && password.length < 19
    }

}