package com.magallanes.photoviewer.domain.model.getSearchPhotos

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photoSize: PhotoSize
)