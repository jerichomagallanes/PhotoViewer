package com.magallanes.photoviewer.presentation.liked_photo_list

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class LikedPhotoListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val likedPhotos: Flow<List<Photo>> = flowOf(emptyList())
)

