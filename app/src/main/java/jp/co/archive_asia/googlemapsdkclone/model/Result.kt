package jp.co.archive_asia.googlemapsdkclone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    var distance: String,
    var time: String
) : Parcelable