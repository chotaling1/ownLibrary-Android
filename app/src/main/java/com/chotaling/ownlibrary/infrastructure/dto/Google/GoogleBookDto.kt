package com.chotaling.ownlibrary.infrastructure.dto.Google

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GoogleBookDto(
    var kind : String,
    var id : String,
    var etag : String,
    var selfLink : String,
    var volumeInfo : VolumeInfoDto
) : Parcelable