package com.magallanes.photoviewer.presentation.liked_photo_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.magallanes.photoviewer.presentation.Screen
import com.magallanes.photoviewer.presentation.liked_photo_list.components.LikedPhotoItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LikedPhotoListScreen(
    navController: NavController,
    viewModel: LikedPhotoListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val likedPhotos by state.likedPhotos.collectAsState(emptyList())
    val isLikedMap = remember { mutableStateMapOf<Int, Boolean>() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp)
        ) {
            stickyHeader {
                Surface(
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Liked Photos",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }

            items(likedPhotos) { photo ->
                val isLiked = isLikedMap[photo.id] ?: likedPhotos.any { it.id == photo.id }

                LikedPhotoItem(
                    photo = photo,
                    onItemClick = {
                        navController.navigate(Screen.PhotoDetailScreen.route + "/${photo.id}")
                    },
                    isLiked = isLiked,
                    onLikeClick = { liked ->
                        if (liked) {
                            viewModel.likePhoto(photo)
                        } else {
                            viewModel.unlikePhoto(photo)
                        }
                        isLikedMap[photo.id] = liked
                    }
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}