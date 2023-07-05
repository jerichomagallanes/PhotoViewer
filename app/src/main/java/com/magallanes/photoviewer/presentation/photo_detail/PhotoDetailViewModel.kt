package com.magallanes.photoviewer.presentation.photo_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magallanes.photoviewer.common.Constants
import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.domain.use_case.get_photo_by_id.GetPhotoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PhotoDetailState())
    val state: State<PhotoDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_PHOTO_ID)?.let { photoId ->
            getPhotoById(id = photoId)
        }
    }

    private fun getPhotoById(id: String) {
        getPhotoByIdUseCase(id = id).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = PhotoDetailState(
                        photoDetail = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = PhotoDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PhotoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}