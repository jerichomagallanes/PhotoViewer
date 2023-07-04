package com.magallanes.photoviewer.domain.model.get_search_photos

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photoSize: PhotoSize
)