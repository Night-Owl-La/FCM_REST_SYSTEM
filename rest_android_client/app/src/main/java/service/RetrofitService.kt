package service

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import vo.MobileMemberVo

interface RetrofitService {

    @GET("/rest_server/mobile_member/id/{id}")
    fun login(@Path("id") id: String): Call<MobileMemberVo>

    @POST("/rest_server/mobile_member")
    fun sighUp(@Body vo: MobileMemberVo): Call<JsonObject>

    //@PUT("/fcmtest/mobile_member/device_token")
    @PUT("/rest_server/mobile_member/device_token")
    fun updateToken(@Body vo: MobileMemberVo) : Call<JsonObject>
}