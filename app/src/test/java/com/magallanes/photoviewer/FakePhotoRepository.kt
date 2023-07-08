package com.magallanes.photoviewer

import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import com.magallanes.photoviewer.mocks.GetPhotoByIdMockResponse
import com.magallanes.photoviewer.mocks.GetSearchPhotosMockResponse


class FakePhotoRepository : PhotoRepository {
    private val photoDetail = GetPhotoByIdMockResponse.getMockPhotoByIdMockResponse()
    private val searchPhotos = GetSearchPhotosMockResponse.getSearchPhotosMockResponse()

    override suspend fun getSearchPhotos(query: String): SearchPhotos {
        return searchPhotos
    }

    override suspend fun getPhotoById(id: String): PhotoDetail {
        return photoDetail
    }
}
