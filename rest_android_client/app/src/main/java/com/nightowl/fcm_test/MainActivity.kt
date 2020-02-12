package com.nightowl.fcm_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_token.setOnClickListener {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(object :
                OnCompleteListener<InstanceIdResult?> {

                override fun onComplete(task: Task<InstanceIdResult?>) {
                    if (!task.isSuccessful) {
                        Log.w("ljw", "getInstanceId failed", task.exception)
                        return
                    }
                    val token: String = task.result!!.token
                    Log.d("test", token) // logCat에 찍힌 token값 복사해두기.
                }

            })
        } // btn_token end

    }
}