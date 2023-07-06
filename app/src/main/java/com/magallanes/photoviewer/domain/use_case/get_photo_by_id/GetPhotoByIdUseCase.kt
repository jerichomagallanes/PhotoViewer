package com.magallanes.photoviewer.domain.use_case.get_photo_by_id

import com.magallanes.photoviewer.common.CustomException
import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(id: String): Flow<Resource<PhotoDetail>> = flow {
        try {
            emit(Resource.Loading<PhotoDetail>())
            val photoDetail = repository.getPhotoById(id = id)
            emit(Resource.Success<PhotoDetail>(photoDetail))
        } catch (e: CustomException) {
            emit(Resource.Error<PhotoDetail>(e.message ?: "An unexpected error occurred"))
        }
    }
}
