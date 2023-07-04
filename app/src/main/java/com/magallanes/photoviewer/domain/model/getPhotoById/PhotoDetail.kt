package com.magallanes.photoviewer.domain.model.getPhotoById

data class PhotoDetail(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Int,
    val alt: String,
    val photoDetailSize: PhotoDetailSize
)