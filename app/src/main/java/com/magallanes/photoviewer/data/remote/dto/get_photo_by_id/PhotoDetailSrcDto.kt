package com.magallanes.photoviewer.data.remote.dto.get_photo_by_id


import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetailSrc
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDetailSrcDto(
    @Json(name = "landscape")
    val landscape: String,
    @Json(name = "large")
    val large: String,
    @Json(name = "large2x")
    val large2x: String,
    @Json(name = "medium")
    val medium: String,
    @Json(name = "original")
    val original: String,
    @Json(name = "portrait")
    val portrait: String,
    @Json(name = "small")
    val small: String,
    @Json(name = "tiny")
    val tiny: String
)

fun PhotoDetailSrcDto.toPhotoDetailSrc(): PhotoDetailSrc {
    return PhotoDetailSrc(
        landscape = landscape,
        large = large,
        large2x = large2x,
        medium = medium,
        original = original,
        portrait = portrait,
        small = small,
        tiny = tiny
    )
}