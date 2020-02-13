package com.nightowl.fcm_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import dao.MobileMemberDao
import kotlinx.android.synthetic.main.activity_login_form.*
import util.showMessageDialog

class LoginFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        btn_login.setOnClickListener {
            val id = et_id.text.toString().trim()
            val pwd = et_pw.text.toString().trim()
            checkLogin(id, pwd)

        }

        btn_signup.setOnClickListener {

        }
    }

    private fun checkLogin(id: String, pwd: String) {

        if (id.isEmpty()) {
            showMessageDialog(this, "", "' ID '  is empty")
            et_id.setText("")
            et_id.requestFocus()
        }

        if (pwd.isEmpty()) {
            showMessageDialog(this, "", "' password ' is empty")
            et_pw.setText("")
            et_pw.requestFocus()
        }

        checkLoginFromDB(id,pwd)
    }

    fun checkLoginFromDB(id: String, pwd: String){
        MobileMemberDao.login(id, pwd, object : Handler(){
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    MobileMemberDao.LOGIN_ID_FAIL -> showMessageDialog(this@LoginFormActivity, "", "아이디가 틀립니다.")
                    MobileMemberDao.LOGIN_PWD_FAIL -> showMessageDialog(this@LoginFormActivity, "", "비밀번호가 틀립니다.")
                    MobileMemberDao.LOGIN_SUCCESS -> {
                        showMessageDialog(this@LoginFormActivity, "", "로그인 성공")
                        startActivity(Intent(this@LoginFormActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        })
    }
}
