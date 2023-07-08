package com.magallanes.photoviewer.repository

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.domain.repository.PhotoDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePhotoDatabaseRepository : PhotoDatabaseRepository {
    private val likedPhotos = MutableStateFlow(emptyList<Photo>())

    override fun getAllLikedPhotos(): Flow<List<Photo>> {
        return likedPhotos
    }

    override suspend fun insertLikedPhoto(photo: Photo) {
        likedPhotos.value = likedPhotos.value + photo
    }

    override suspend fun deleteLikedPhoto(photo: Photo) {
        likedPhotos.value = likedPhotos.value - photo
    }
}