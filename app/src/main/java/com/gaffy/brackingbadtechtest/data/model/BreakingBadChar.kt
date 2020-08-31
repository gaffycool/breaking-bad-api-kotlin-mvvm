package com.gaffy.brackingbadtechtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BreakingBadChar(
    @SerializedName("char_id") val charId: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("birthday") val birthday: String = "",
    @SerializedName("occupation") val occupation: List<String> = emptyList(),
    @SerializedName("img") val img: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("nickname") val nickname: String = "",
    @SerializedName("appearance") val appearance: List<Int> = emptyList(),
    @SerializedName("portrayed") val portrayed: String = "",
    @SerializedName("category") val category: String = ""
) : Parcelable