package com.magallanes.photoviewer.domain.model.get_search_photos

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Int,
    val alt: String,
    val src: PhotoSrc
)