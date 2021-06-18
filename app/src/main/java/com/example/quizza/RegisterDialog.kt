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
import androidx.lifecycle.Observer
import com.example.quizza.entities.User
import com.example.quizza.viewmodels.LogRegViewModel
import kotlinx.android.synthetic.main.register_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterDialog(myContext: Context): DialogFragment() {

    private val appContext = myContext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView: View = inflater.inflate(R.layout.register_dialog, container, false)

        rootView.bgRegisterDialog.setBackgroundResource(AppColor.getBackgroundColor())

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

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        })

        rootView.btnRegister.setOnClickListener{
            if (validateInput(rootView.etUsernameReg.text.toString(), rootView.etEmailReg.text.toString(), rootView.etpPasswordReg.text.toString())){
                val db = DBManager.getInstance(appContext)
                val userDao = db.userDAO()

                val user = User()
                user.username = rootView.etUsernameReg.text.toString()
                user.password = rootView.etpPasswordReg.text.toString()
                user.email= rootView.etEmailReg.text.toString()
                user.avatar = rootView.spinnerAvatar.selectedItem.toString()
                user.totalScore = 0

                CoroutineScope(Dispatchers.IO).launch {
                    val userExists = userDao.checkUserExists(user.username)

                    if(userExists != null)
                        LogRegViewModel.registerFail()
                    else{
                        userDao.insertNewUser(user)
                        LogRegViewModel.registerSuccess()
                    }
                }
            } else
                Toast.makeText(appContext, getString(R.string.wrong_credentials_dialog), Toast.LENGTH_SHORT).show()
        }

        setViewModel()
        return rootView
    }

    private fun setViewModel(){
        val registerObserver = Observer<Boolean>{
            if(LogRegViewModel.isRegistered.value == true)
                Toast.makeText(appContext, getString(R.string.user_can_login_text), Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(appContext, getString(R.string.wrong_credentials_dialog), Toast.LENGTH_SHORT).show()
        }
        LogRegViewModel.isRegistered.observe(this, registerObserver)
    }

    fun validateInput(username: String, email: String, password: String): Boolean{
        //assuming that both username and password must be no longer than 18 char and no shorter than 5 char
        return username.length < 19 && username.length > 4 && email.length > 4 && email.length < 21 && password.length > 4 && password.length < 19
    }
}