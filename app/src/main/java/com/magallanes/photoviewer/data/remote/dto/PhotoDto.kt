package com.magallanes.photoviewer.data.remote

import com.magallanes.photoviewer.data.remote.dto.Size

data class PhotoDto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: Int,
    val photographer: Int,
    val photographer_url: Int,
    val photographer_id: Int,
    val avg_color: Int,
    val src: Size,
    val alt: String
)
