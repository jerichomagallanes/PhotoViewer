package com.magallanes.photoviewer.domain.repository

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoDatabaseRepository {
    fun getAllLikedPhotos(): Flow<List<Photo>>
    suspend fun insertLikedPhoto(photo: Photo)
    suspend fun deleteLikedPhoto(photo: Photo)
}