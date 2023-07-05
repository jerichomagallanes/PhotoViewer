package com.magallanes.photoviewer.domain.model.get_photo_by_id

data class PhotoDetail(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Int,
    val alt: String,
    val photoDetailSize: PhotoDetailSize
)