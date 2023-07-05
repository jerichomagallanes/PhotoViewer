package com.magallanes.photoviewer.presentation.photo_list

import com.magallanes.photoviewer.domain.use_case.get_search_photos.GetSearchPhotosUseCase
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magallanes.photoviewer.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getSearchPhotosUseCase: GetSearchPhotosUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PhotoListState())
    val state: State<PhotoListState> = _state

    fun getSearchPhotos(query: String) {
        getSearchPhotosUseCase(query = query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PhotoListState(
                        photos = result.data?.photos ?: emptyList(),
                        totalResults = result.data?.totalResults ?: 0,
                        query = query
                    )
                }
                is Resource.Error -> {
                    _state.value = PhotoListState(
                        error = result.message ?: "An unexpected error occurred",
                        query = query
                    )
                }
                is Resource.Loading -> {
                    _state.value = PhotoListState(
                        isLoading = true,
                        query = query
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}