package com.magallanes.photoviewer.domain.model.getSearchPhotos

import com.magallanes.photoviewer.data.remote.dto.getSearchPhotos.PhotoDto

data class SearchPhotos(
    val photos: List<PhotoDto>,
    val total_results: Int
)