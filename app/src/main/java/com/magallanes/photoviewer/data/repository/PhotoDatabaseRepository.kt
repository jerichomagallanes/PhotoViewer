package com.magallanes.photoviewer.data.repository

import com.magallanes.photoviewer.data.local.PhotoDao
import com.magallanes.photoviewer.data.local.PhotoMapper
import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoDatabaseRepository @Inject constructor(
    private val photoDao: PhotoDao
) {
    private val photoMapper = PhotoMapper()

    fun getAllLikedPhotos(): Flow<List<Photo>> {
        return photoDao.getAllPhotos().map { photoEntities ->
            photoEntities.map { photoEntity ->
                photoMapper.mapToDomain(photoEntity)
            }
        }
    }

    suspend fun insertLikedPhoto(photo: Photo) {
        val photoEntity = photoMapper.mapToEntity(photo)
        photoDao.insertPhoto(photoEntity)
    }

    suspend fun deleteLikedPhoto(photo: Photo) {
        val photoEntity = photoMapper.mapToEntity(photo)
        photoDao.deletePhoto(photoEntity)
    }
}

