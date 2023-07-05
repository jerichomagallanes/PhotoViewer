package com.magallanes.photoviewer.presentation

sealed class Screen(val route: String) {
    object PhotoListScreen: Screen("photo_list")
    object PhotoDetailScreen: Screen("photo_detail_screen")
}
