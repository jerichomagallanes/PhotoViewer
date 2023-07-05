package com.magallanes.photoviewer.domain.use_case.get_photo_by_id

import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.toPhotoDetail
import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(id: String): Flow<Resource<PhotoDetail>> = flow {
        try {
            emit(Resource.Loading<PhotoDetail>())
            val photoDetail = repository.getPhotoById(id = id).toPhotoDetail()
            emit(Resource.Success<PhotoDetail>(photoDetail))
        } catch(e: HttpException) {
            emit(Resource.Error<PhotoDetail>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<PhotoDetail>("Couldn't reach server. Check your internet connection."))
        } catch (e: JsonDataException) {
            emit(Resource.Error<PhotoDetail>("Invalid response data."))
        }
    }
}