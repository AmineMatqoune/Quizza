package com.example.quizza

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.quizza.entities.User
import kotlinx.android.synthetic.main.login_dialog.view.*
import kotlinx.android.synthetic.main.register_dialog.*
import kotlinx.android.synthetic.main.register_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterDialog(myContext: Context): DialogFragment() {

    private val appContext = myContext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView: View = inflater.inflate(R.layout.register_dialog, container, false)

        rootView.spinnerAvatar.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                when(rootView.spinnerAvatar.selectedItem.toString()){
                    "Joe" -> rootView.ivAvatar.setBackgroundResource(R.drawable.joe)
                    "Lara" -> rootView.ivAvatar.setBackgroundResource(R.drawable.lara)
                    "Mike" -> rootView.ivAvatar.setBackgroundResource(R.drawable.mike)
                    "Meg" -> rootView.ivAvatar.setBackgroundResource(R.drawable.meg)
                    "Boe" -> rootView.ivAvatar.setBackgroundResource(R.drawable.boe)
                    "Rosa" -> rootView.ivAvatar.setBackgroundResource(R.drawable.rosa)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        })

        rootView.btnRegister.setOnClickListener{
            if (validateInput(rootView.etUsernameReg.text.toString(), rootView.etEmailReg.text.toString(), rootView.etpPasswordReg.text.toString())){
                val db = DBManager.getInstance(appContext)
                val userDao = db.userDAO()

                val user = User( )
                user.username = rootView.etUsernameReg.text.toString()
                user.password = rootView.etpPasswordReg.text.toString()
                user.email= rootView.etEmailReg.text.toString()
                user.avatar = rootView.spinnerAvatar.selectedItem.toString()
                user.totalScore = 0

                Toast.makeText(appContext, rootView.spinnerAvatar.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    userDao.insertNewUser(user)
                    println(getString(R.string.register_welcome))
                }
            } else
                Toast.makeText(appContext, getString(R.string.wrong_credentials_dialog), Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

    fun validateInput(username: String, email: String, password: String): Boolean{
        //assuming that both username and password must be no longer than 18 char and no shorter than 5 char
        return username.length < 19 && username.length > 5 && email.length > 4 && email.length < 21 && password.length > 4 && password.length < 19
    }
}