package vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MobileMemberVo(
    var idx: Int = 0,
    var name: String = "0",
    var id: String = "0",
    var pwd: String = "0",
    var device_token: String = "0"
) : Parcelable