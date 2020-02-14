package dao

import NightOwl_Util.MyConstant
import NightOwl_Util.MyShareData
import android.os.Handler
import android.os.Message
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.RetrofitHelper
import util.logCatSimple
import util.toastShortSimple
import vo.MobileMemberVo

object MobileMemberDao {
    const val LOGIN_SUCCESS = 0
    const val LOGIN_ID_FAIL = 1
    const val LOGIN_PWD_FAIL = 2
    const val LOGIN_SERVER_CONNECT_FAIL =3

    fun login(id: String, pwd: String, handler: Handler) {
        RetrofitHelper.getService().login(id)
            .enqueue(object : Callback<MobileMemberVo> {
                override fun onResponse( call: Call<MobileMemberVo>, response: Response<MobileMemberVo> ) {
                    if (response.isSuccessful) {
                        val user = response.body() ?: MobileMemberVo()

                        when {
                            user.idx == 0 -> {
                                val msg = Message()
                                msg.what = LOGIN_ID_FAIL
                                msg.obj = "아이디가 틀립니다."
                                handler.sendMessage(msg)
                            }
                            user.pwd != pwd -> {
                                val msg = Message()
                                msg.what = LOGIN_PWD_FAIL
                                msg.obj = "비밀번호가 틀립니다."
                                handler.sendMessage(msg)
                            }
                            else -> {
                                logCatSimple(user.toString())
                                MyShareData.user = user
                                handler.sendEmptyMessage(LOGIN_SUCCESS)
                            }
                        }
                    } // if end
                }

                override fun onFailure(call: Call<MobileMemberVo>, t: Throwable) {
                    val msg = Message()
                    msg.what = LOGIN_SERVER_CONNECT_FAIL
                    msg.obj = "서버에서 데이터를 받아오지 못했습니다!"
                    handler.sendMessage(msg)
                }
            })
    }

    fun sendDeviceToken() {
        RetrofitHelper.getService().updateToken(MyShareData.user)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) toastShortSimple("알림에 동의하셨습니다.")
                    else toastShortSimple("요청이 실패했습니다.")
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    toastShortSimple("요청이 실패했습니다.")
                }
            })
    }

    fun signUp(user:MobileMemberVo, handler: Handler){
        RetrofitHelper.getService().sighUp(user)
            .enqueue(object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    when(response.body()!!.get("result").asInt){
                        MyConstant.SUCCESS -> handler.sendEmptyMessage(MyConstant.SUCCESS)
                        MyConstant.FAIL -> {
                            val msg = Message()
                            msg.what = MyConstant.FAIL
                            msg.obj = "요청이 유효하지 않습니다."
                            handler.sendMessage(msg)
                        }
                    }

                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    val msg = Message()
                    msg.what = MyConstant.FAIL
                    msg.obj = "서버에서 데이터를 받아오지 못했습니다!"
                    handler.sendMessage(msg)
                }
            })
    }
}