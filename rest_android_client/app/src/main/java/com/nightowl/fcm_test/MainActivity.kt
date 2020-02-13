package com.nightowl.fcm_test

import NightOwl_Util.MyShareData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import dao.MobileMemberDao
import kotlinx.android.synthetic.main.activity_main.*
import util.logCatSimple
import util.showConfirmDialog
import util.toastShortSimple

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_token.setOnClickListener { checkReceiveCloudMessageOK() } // btn_token end

        btn_loginform.setOnClickListener {
            startActivity(Intent(this, LoginFormActivity::class.java))
        }
    }

    private fun checkReceiveCloudMessageOK(){
        showConfirmDialog(this,"", "알림메시지를 받으시겠습니까?", object :Handler(){
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    0 -> return
                    1 -> sendDeviceToken()
                }
            }
        })
    }

    fun sendDeviceToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(object :
            OnCompleteListener<InstanceIdResult?> {

            override fun onComplete(task: Task<InstanceIdResult?>) {
                if (!task.isSuccessful) {
                    Log.w("ljw", "getInstanceId failed", task.exception)
                    return
                }
                val token: String = task.result!!.token
                if(MyShareData.user.idx == 0){
                    toastShortSimple("로그인 후 이용 가능한 기능 입니다.")
                    return
                }
                MyShareData.user.device_token = token
                MobileMemberDao.sendDeviceToken()
                toastShortSimple(MyShareData.user.device_token)
            }

        })
    }
}