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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhotoListScreen(
    navController: NavController,
    versionName: String,
    viewModel: PhotoListViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val state = viewModel.state.value
    val likedPhotos by state.likedPhotos.collectAsState(emptyList())
    val isLikedMap = remember { mutableStateMapOf<Int, Boolean>() }
    val searchQuery = remember { mutableStateOf("") }
    var textFieldFocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scaffoldState = rememberScaffoldState()
    val isDrawerOpen = scaffoldState.drawerState.isOpen

    val isDarkModeOn = remember { mutableStateOf(viewModel.isDarkModeOn) }

    LaunchedEffect(Unit) {
        textFieldFocus = true
        focusRequester.requestFocus()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_gear),
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        drawerContent = {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Dark Mode",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isDarkModeOn.value,
                            onCheckedChange = { enabled ->
                                isDarkModeOn.value = enabled
                                viewModel.setDarkModePreference(enabled)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colors.primary,
                                checkedTrackColor = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
                Text(
                    text = "v$versionName",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        },
        drawerBackgroundColor = MaterialTheme.colors.surface,
        drawerContentColor = MaterialTheme.colors.onSurface,
        drawerElevation = DrawerDefaults.Elevation,
        drawerGesturesEnabled = isDrawerOpen,
        content = { paddingValues ->
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colors.onPrimary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    TopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
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
                                    },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.LikedPhotoListScreen.route)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_heart_filled),
                                    contentDescription = "Favorites",
                                    tint = MaterialTheme.colors.primary
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f) // Consume remaining space
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.photos) { photo ->
                                val isLiked =
                                    isLikedMap[photo.id] ?: likedPhotos.any { it.id == photo.id }

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

                        if (state.error.isNotBlank()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.error,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }

                        if (state.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    )
}