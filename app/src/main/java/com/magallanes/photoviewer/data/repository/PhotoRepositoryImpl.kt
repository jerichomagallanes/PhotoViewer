package com.magallanes.photoviewer.data.repository

import com.magallanes.photoviewer.data.remote.PexelsApi
import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.PhotoDetailDto
import com.magallanes.photoviewer.data.remote.dto.get_search_photos.SearchPhotosDto
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PexelsApi
): PhotoRepository {
    override suspend fun getSearchPhotos(query: String): SearchPhotosDto {
        return api.getSearchPhotos(query = query)
    }

    override suspend fun getPhotoById(id: String): PhotoDetailDto {
        return api.getPhotoById(id = id)
    }
}