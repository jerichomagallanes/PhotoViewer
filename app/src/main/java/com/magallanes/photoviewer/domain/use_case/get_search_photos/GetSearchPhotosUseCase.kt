package com.magallanes.photoviewer.domain.use_case.get_search_photos

import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.data.remote.dto.get_search_photos.toSearchPhotos
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(query: String): Flow<Resource<SearchPhotos>> = flow {
        try {
            emit(Resource.Loading<SearchPhotos>())
            val searchPhotos = repository.getSearchPhotos(query = query).toSearchPhotos()
            emit(Resource.Success<SearchPhotos>(searchPhotos))
        } catch(e: HttpException) {
            emit(Resource.Error<SearchPhotos>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<SearchPhotos>("Couldn't reach server. Check your internet connection."))
        }
    }
}