package com.magallanes.photoviewer.data.remote.dto.get_photo_by_id


import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail
import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetailSize
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PhotoDetailDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "photographer")
    val photographer: String,
    @Json(name = "photographer_url")
    val photographer_url: String,
    @Json(name = "photographer_id")
    val photographer_id: Int,
    @Json(name = "avg_color")
    val avg_color: String,
    @Json(name = "alt")
    val alt: String,
    @Json(name = "src")
    val photoDetailSize: PhotoDetailSize,
    @Json(name = "liked")
    val liked: Boolean
)

fun PhotoDetailDto.toPhotoDetail(): PhotoDetail {
    return PhotoDetail(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographer_url = photographer_url,
        photographer_id = photographer_id,
        alt = alt,
        photoDetailSize = photoDetailSize
    )
}