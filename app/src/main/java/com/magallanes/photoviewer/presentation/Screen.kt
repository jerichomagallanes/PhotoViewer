package com.magallanes.photoviewer.presentation

sealed class Screen(val route: String) {
    object PhotoListScreen: Screen("photo_list_screen")
    object PhotoDetailScreen: Screen("photo_detail_screen")
    object LikedPhotoListScreen: Screen("liked_photo_list_screen")
}
