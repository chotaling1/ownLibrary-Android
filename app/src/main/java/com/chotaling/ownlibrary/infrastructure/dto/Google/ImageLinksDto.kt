package com.chotaling.ownlibrary.infrastructure.dto.Google

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ImageLinksDto (
    var smallThumbnail : String?,
    var thumbnail : String?
) : Parcelable