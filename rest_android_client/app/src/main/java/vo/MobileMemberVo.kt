package vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MobileMemberVo(
    var idx: Int,
    var name: String,
    var id: String,
    var pwd: String,
    var device_token: String
) : Parcelable