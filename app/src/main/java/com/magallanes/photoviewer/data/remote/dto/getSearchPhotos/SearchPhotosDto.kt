package com.magallanes.photoviewer.data.remote.dto.getSearchPhotos

import com.magallanes.photoviewer.domain.model.getSearchPhotos.SearchPhotos
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPhotosDto(
    @Json(name = "next_page")
    val next_page: String,
    @Json(name = "page")
    val page: Int,
    @Json(name = "per_page")
    val per_page: Int,
    @Json(name = "photos")
    val photos: List<PhotoDto>,
    @Json(name = "total_results")
    val total_results: Int
)

fun SearchPhotosDto.toSearchPhotos(): SearchPhotos {
    return SearchPhotos(
        photos = photos,
        total_results = total_results
    )
}