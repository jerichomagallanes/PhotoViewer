package com.magallanes.photoviewer.domain.use_case.get_search_photos

import com.magallanes.photoviewer.common.CustomException
import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(query: String): Flow<Resource<SearchPhotos>> = flow {
        try {
            emit(Resource.Loading<SearchPhotos>())
            val searchPhotos = repository.getSearchPhotos(query = query)
            emit(Resource.Success<SearchPhotos>(searchPhotos))
        } catch (e: CustomException) {
            emit(Resource.Error<SearchPhotos>(e.message ?: "An unexpected error occurred"))
        }
    }
}