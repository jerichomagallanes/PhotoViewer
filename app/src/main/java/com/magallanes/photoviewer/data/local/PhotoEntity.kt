package com.magallanes.photoviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.magallanes.photoviewer.domain.model.get_search_photos.PhotoSrc

@Entity
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String?,
    val photographerId: Int,
    val alt: String,
    val src: PhotoSrc
)