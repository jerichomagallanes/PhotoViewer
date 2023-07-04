package com.magallanes.photoviewer.data.remote

import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.PhotoDetailDto
import com.magallanes.photoviewer.data.remote.dto.get_search_photos.SearchPhotosDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {

    @GET("/v1/search")
    suspend fun getSearchPhotos(
        @Query("query") query: String
    ): SearchPhotosDto

    @GET("/v1/photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String
    ): PhotoDetailDto
}
