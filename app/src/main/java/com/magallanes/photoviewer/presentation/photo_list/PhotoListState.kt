package com.magallanes.photoviewer.presentation.photo_list

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class PhotoListState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val totalResults: Int = 0,
    val error: String = "",
    val query: String = "",
    val likedPhotos: Flow<List<Photo>> = flowOf(emptyList())
)

