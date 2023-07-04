package com.magallanes.photoviewer.domain.repository

import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.PhotoDetailDto
import com.magallanes.photoviewer.data.remote.dto.get_search_photos.SearchPhotosDto


interface PhotoRepository {

    suspend fun getSearchPhotos(query: String): SearchPhotosDto

    suspend fun getPhotoById(id: String): PhotoDetailDto
}