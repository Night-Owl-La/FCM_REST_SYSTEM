package dao

import NightOwl_Util.MyShareData
import android.os.Handler
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.RetrofitHelper
import util.logCatSimple
import vo.MobileMemberVo

object MobileMemberDao {
    const val LOGIN_SUCCESS = 0
    const val LOGIN_ID_FAIL = 1
    const val LOGIN_PWD_FAIL = 2

    fun login(id: String, pwd: String, handler: Handler) {
        RetrofitHelper.getService().login(id)
            .enqueue(object : Callback<MobileMemberVo> {
                override fun onResponse(
                    call: Call<MobileMemberVo>,
                    response: Response<MobileMemberVo>
                ) {
                    if (response.isSuccessful) {
                        logCatSimple("## request login OK ##")
                        val user = response.body() ?: MobileMemberVo(0, "0", "0", "0", "0")

                        when {
                            user.idx == 0 -> {
                                handler.sendEmptyMessage(LOGIN_ID_FAIL)
                            }
                            user.pwd != pwd -> {
                                handler.sendEmptyMessage(LOGIN_PWD_FAIL)
                            }
                            else -> {
                                handler.sendEmptyMessage(LOGIN_SUCCESS)
                                MyShareData.user = user
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MobileMemberVo>, t: Throwable) {
                    logCatSimple("## request login Fail ##")
                }
            })
    }

    fun sendDeviceToken() {
        RetrofitHelper.getService().updateToken(MyShareData.user)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        logCatSimple("## request send Token OK ##")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    logCatSimple("## request send Token Fail ##")
                }
            })
    }
}

/*
var vo1 = RetrofitHelper.getService().login("one")
            logCatSimple("## click ok ##")
            vo1.enqueue(object : Callback<MobileMemberVo>{

                override fun onResponse( call: Call<MobileMemberVo>, response: Response<MobileMemberVo> ) {
                    logCatSimple("## onResponse ##")
                    var vo2 = response.body()
                    logCatSimple(vo2.toString())
                }

                override fun onFailure(call: Call<MobileMemberVo>, t: Throwable) {
                    logCatSimple("## onFailure ##")
                }
            })
 */