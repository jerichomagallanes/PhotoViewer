package com.magallanes.photoviewer.data.repository

import com.magallanes.photoviewer.common.CustomException
import com.magallanes.photoviewer.data.remote.PexelsApi
import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.toPhotoDetail
import com.magallanes.photoviewer.data.remote.dto.get_search_photos.toSearchPhotos
import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PexelsApi
) : PhotoRepository {
    override suspend fun getSearchPhotos(query: String): SearchPhotos {
        try {
            val searchPhotosDto = api.getSearchPhotos(query = query)
            return searchPhotosDto.toSearchPhotos()
        } catch (e: HttpException) {
            throw CustomException("An unexpected error occurred: ${e.localizedMessage}")
        } catch (e: IOException) {
            throw CustomException("Couldn't reach the server. Check your internet connection.")
        } catch (e: JsonDataException) {
            throw CustomException("Invalid response data.")
        }
    }

    override suspend fun getPhotoById(id: String): PhotoDetail {
        if (id.isEmpty()) {
            throw CustomException("Invalid photo ID")
        }

        try {
            val photoDetailDto = api.getPhotoById(id)
            return photoDetailDto.toPhotoDetail()
        } catch (e: HttpException) {
            throw CustomException("An unexpected error occurred: ${e.localizedMessage}")
        } catch (e: IOException) {
            throw CustomException("Couldn't reach the server. Check your internet connection.")
        } catch (e: JsonDataException) {
            throw CustomException("Invalid response data.")
        }
    }
}
