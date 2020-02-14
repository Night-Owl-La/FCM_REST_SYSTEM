package com.nightowl.fcm_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import dao.MobileMemberDao
import kotlinx.android.synthetic.main.activity_login_form.*
import util.logCatSimple
import util.showMessageDialog

class LoginFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        btn_login.setOnClickListener {
            val id = et_id.text.toString().trim()
            val pwd = et_pw.text.toString().trim()
            requestLogin(id, pwd)
        }

        btn_locate_signup.setOnClickListener { startActivity(Intent(this, SignUpFormActivity::class.java)) }
    }

    private fun requestLogin(id: String, pwd: String) {

        if (id.isEmpty()) {
            showMessageDialog(this, "", "' ID '  is empty")
            et_id.setText("")
            et_id.requestFocus()
            return
        }

        if (pwd.isEmpty()) {
            showMessageDialog(this, "", "' Password ' is empty")
            et_pw.setText("")
            et_pw.requestFocus()
            return
        }

        checkUserDataFromDB(id,pwd)
    }

    private fun checkUserDataFromDB(id: String, pwd: String){
        MobileMemberDao.login(id, pwd, object : Handler(){
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    MobileMemberDao.LOGIN_SUCCESS -> {
                        showMessageDialog(this@LoginFormActivity, "", "로그인 성공")
                        startActivity(Intent(this@LoginFormActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                    else -> showMessageDialog(this@LoginFormActivity, msg.what.toString(), msg.obj.toString())
                }//when end
            }
        }) //Dao end
    }

}