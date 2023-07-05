package com.magallanes.photoviewer.data.remote.dto.get_search_photos

import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPhotosDto(
    @Json(name = "next_page")
    val nextPage: String,
    @Json(name = "page")
    val page: Int,
    @Json(name = "per_page")
    val perPage: Int,
    @Json(name = "photos")
    val photos: List<PhotoDto>,
    @Json(name = "total_results")
    val totalResults: Int
)

fun SearchPhotosDto.toSearchPhotos(): SearchPhotos {
    return SearchPhotos(
        photos = photos.map { it.toPhoto() },
        totalResults = totalResults
    )
}