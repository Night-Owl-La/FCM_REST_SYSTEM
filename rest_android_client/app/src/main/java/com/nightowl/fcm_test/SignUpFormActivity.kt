package com.nightowl.fcm_test

import NightOwl_Util.MyConstant
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import dao.MobileMemberDao
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import util.showMessageDialog
import util.toastShortSimple
import vo.MobileMemberVo

class SignUpFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)

        btn_signup.setOnClickListener { checkSignUpData() }
        btn_signup_cancle.setOnClickListener { finish() }
    }
    
    private fun checkSignUpData(){
        val name = et_signup_name.text.toString().trim()
        val id = et_signup_id.text.toString().trim()
        val pw = et_signup_pw.text.toString().trim()

        when{
            name.isEmpty() -> {
                toastShortSimple("이름을 입력하세요.")
                et_signup_name.setText("")
                et_signup_name.requestFocus()
                return 
            }

            id.isEmpty() -> {
                toastShortSimple("ID를 입력하세요.")
                et_signup_id.setText("")
                et_signup_id.requestFocus()
                return
            }

            pw.isEmpty() -> {
                toastShortSimple("비밀번호를 입력하세요.")
                et_signup_pw.setText("")
                et_signup_pw.requestFocus()
                return
            }

            else -> {
                val user = MobileMemberVo(name = name, id = id, pwd = pw, idx = 0, device_token = "0")
                MobileMemberDao.signUp(user, object : Handler(){
                    override fun handleMessage(msg: Message) {
                        when(msg.what){
                            MyConstant.SUCCESS -> {
                                toastShortSimple("가입 성공")
                                finish()
                            }
                            MyConstant.FAIL -> {
                                showMessageDialog(this@SignUpFormActivity, "", msg.obj.toString())
                            }

                        }
                    }
                })
            } // else end
        }// when end
    }

}
