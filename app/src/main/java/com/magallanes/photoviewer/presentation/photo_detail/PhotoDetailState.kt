package com.magallanes.photoviewer.presentation.photo_detail

import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photoDetail: PhotoDetail? = null,
    val error: String = ""
)
