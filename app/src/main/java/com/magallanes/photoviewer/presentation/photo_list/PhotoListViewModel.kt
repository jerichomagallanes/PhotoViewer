package com.magallanes.photoviewer.presentation.photo_list

import android.content.SharedPreferences
import com.magallanes.photoviewer.domain.use_case.get_search_photos.GetSearchPhotosUseCase
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magallanes.photoviewer.common.Constants.DARK_MODE
import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.domain.repository.PhotoDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getSearchPhotosUseCase: GetSearchPhotosUseCase,
    private val photoListDatabaseRepository: PhotoDatabaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = mutableStateOf(PhotoListState())
    val state: State<PhotoListState> = _state
    var isDarkModeOn: Boolean
        get() = sharedPreferences.getBoolean(DARK_MODE, false)
        private set(value) {
            sharedPreferences.edit().putBoolean(DARK_MODE, value).apply()
        }
    fun setDarkModePreference(enabled: Boolean) {
        isDarkModeOn = enabled
        sharedPreferences.edit().putBoolean(DARK_MODE, enabled).apply()
    }

    fun getSearchPhotos(query: String) {
        getSearchPhotosUseCase(query = query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PhotoListState(
                        photos = result.data?.photos ?: emptyList(),
                        totalResults = result.data?.totalResults ?: 0,
                        query = query,
                        likedPhotos = photoListDatabaseRepository.getAllLikedPhotos()
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

    fun likePhoto(photo: Photo) {
        viewModelScope.launch {
            photoListDatabaseRepository.insertLikedPhoto(photo)
        }
    }

    fun unlikePhoto(photo: Photo) {
        viewModelScope.launch {
            photoListDatabaseRepository.deleteLikedPhoto(photo)
        }
    }

}