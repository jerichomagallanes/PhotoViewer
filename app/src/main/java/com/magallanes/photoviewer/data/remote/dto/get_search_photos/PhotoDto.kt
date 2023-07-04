package com.magallanes.photoviewer.data.remote.dto.get_search_photos

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDto(
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
    val photoSize: PhotoSizeDto,
    @Json(name = "liked")
    val liked: Boolean
)

fun PhotoDto.toPhoto(): Photo {
    return Photo(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photoSize = photoSize.toPhotoSize()
    )
}