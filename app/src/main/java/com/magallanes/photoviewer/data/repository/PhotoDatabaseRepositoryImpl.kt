package com.magallanes.photoviewer.data.repository

import com.magallanes.photoviewer.data.local.PhotoDao
import com.magallanes.photoviewer.data.local.PhotoMapper
import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.domain.repository.PhotoDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoDatabaseRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao
): PhotoDatabaseRepository {
    private val photoMapper = PhotoMapper()

    override fun getAllLikedPhotos(): Flow<List<Photo>> {
        return photoDao.getAllPhotos().map { photoEntities ->
            photoEntities.map { photoEntity ->
                photoMapper.mapToDomain(photoEntity)
            }
        }
    }

    override suspend fun insertLikedPhoto(photo: Photo) {
        val photoEntity = photoMapper.mapToEntity(photo)
        photoDao.insertPhoto(photoEntity)
    }

    override suspend fun deleteLikedPhoto(photo: Photo) {
        val photoEntity = photoMapper.mapToEntity(photo)
        photoDao.deletePhoto(photoEntity)
    }
}

