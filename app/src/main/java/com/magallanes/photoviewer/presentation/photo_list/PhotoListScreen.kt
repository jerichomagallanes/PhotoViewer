package com.magallanes.photoviewer.presentation.photo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.magallanes.photoviewer.R
import com.magallanes.photoviewer.presentation.Screen
import com.magallanes.photoviewer.presentation.photo_list.components.PhotoItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhotoListScreen(
    navController: NavController,
    viewModel: PhotoListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val likedPhotos by state.likedPhotos.collectAsState(emptyList())
    val isLikedMap = remember { mutableStateMapOf<Int, Boolean>() }
    val searchQuery = remember { mutableStateOf("") }
    var textFieldFocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        textFieldFocus = true
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp)
        ) {
            items(state.photos) { photo ->
                val isLiked = isLikedMap[photo.id] ?: likedPhotos.any { it.id == photo.id }

                PhotoItem(
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

        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    placeholder = { Text("Search") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.getSearchPhotos(query = searchQuery.value)
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.Transparent)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            textFieldFocus = it.isFocused
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        navController.navigate(Screen.LikedPhotoListScreen.route)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_heart_filled),
                        contentDescription = "Favorites"
                    )
                }
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
