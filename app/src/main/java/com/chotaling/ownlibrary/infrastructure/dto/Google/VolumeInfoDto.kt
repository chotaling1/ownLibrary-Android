package com.chotaling.ownlibrary.infrastructure.dto.Google

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class VolumeInfoDto (
    var title: String,
    var authors: Array<String>,
    var publisher: String,
    var publishedDate: String,
    var description: String,
    var pageCount: Int,
    var category: String,
    var imageLinks: ImageLinksDto?
) : Parcelable