package com.magallanes.photoviewer.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.magallanes.photoviewer.common.Constants
import com.magallanes.photoviewer.presentation.liked_photo_list.LikedPhotoListScreen
import com.magallanes.photoviewer.presentation.photo_detail.PhotoDetailScreen
import com.magallanes.photoviewer.presentation.photo_list.PhotoListScreen
import com.magallanes.photoviewer.presentation.ui.theme.PhotoViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Constants.KEY_SHARED_PREF, Context.MODE_PRIVATE)
    val isDarkModeOn = remember {
        mutableStateOf(
            sharedPreferences.getBoolean(Constants.DARK_MODE, false)
        )
    }

    DisposableEffect(Unit) {
        val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == Constants.DARK_MODE) {
                isDarkModeOn.value = sharedPreferences.getBoolean(Constants.DARK_MODE, false)
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        onDispose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        }
    }

    PhotoViewerTheme(darkTheme = isDarkModeOn.value) {
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.PhotoListScreen.route
            ) {
                composable(
                    route = Screen.PhotoListScreen.route
                ) {
                    PhotoListScreen(navController = navController)
                }
                composable(
                    route = Screen.PhotoDetailScreen.route + "/{id}"
                ) {
                    PhotoDetailScreen()
                }
                composable(
                    route = Screen.LikedPhotoListScreen.route
                ) {
                    LikedPhotoListScreen(navController = navController)
                }
            }
        }
    }
}
