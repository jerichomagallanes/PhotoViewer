package com.magallanes.photoviewer.domain.model.get_search_photos

data class SearchPhotos(
    val photos: List<Photo>,
    val total_results: Int
)