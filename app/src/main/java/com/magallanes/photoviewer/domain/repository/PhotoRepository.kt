package com.magallanes.photoviewer.domain.repository

import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos


interface PhotoRepository {

    suspend fun getSearchPhotos(query: String): SearchPhotos

    suspend fun getPhotoById(id: String): PhotoDetail
}