package com.magallanes.photoviewer.presentation.liked_photo_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.domain.repository.PhotoDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedPhotoListViewModel @Inject constructor(
    private val photoListDatabaseRepository: PhotoDatabaseRepository
) : ViewModel() {

    private val _state = mutableStateOf(LikedPhotoListState())
    val state: State<LikedPhotoListState> = _state

    private val likedPhotoCount = mutableStateOf(0)

    init {
        refreshLikedPhotos()
    }

    fun likePhoto(photo: Photo) {
        viewModelScope.launch {
            photoListDatabaseRepository.insertLikedPhoto(photo)
            refreshLikedPhotos()
        }
    }

    fun unlikePhoto(photo: Photo) {
        viewModelScope.launch {
            photoListDatabaseRepository.deleteLikedPhoto(photo)
            refreshLikedPhotos()
        }
    }

    private fun refreshLikedPhotos() {
        viewModelScope.launch {
            val likedPhotos = photoListDatabaseRepository.getAllLikedPhotos().firstOrNull()
            likedPhotoCount.value = likedPhotos?.size ?: 0
            refreshState()
        }
    }

    private fun refreshState() {
        _state.value = if (likedPhotoCount.value > 0) {
            LikedPhotoListState(likedPhotos = photoListDatabaseRepository.getAllLikedPhotos())
        } else {
            LikedPhotoListState(error = "You have no liked photos")
        }
    }
}
