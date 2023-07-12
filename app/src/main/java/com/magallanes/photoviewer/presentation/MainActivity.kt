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
import com.magallanes.photoviewer.BuildConfig
import com.magallanes.photoviewer.common.Constants
import com.magallanes.photoviewer.presentation.liked_photo_list.LikedPhotoListScreen
import com.magallanes.photoviewer.presentation.photo_detail.PhotoDetailScreen
import com.magallanes.photoviewer.presentation.photo_list.PhotoListScreen
import com.magallanes.photoviewer.presentation.splash_screen.SplashScreen
import com.magallanes.photoviewer.presentation.ui.theme.PhotoViewerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val versionName = BuildConfig.VERSION_NAME

        setContent {
            AppContent(versionName = versionName)
        }
    }
}

@Composable
fun AppContent(versionName: String) {
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

    val navController = rememberNavController()

    val splashScreenDuration = 2000L // 2 seconds

    var showSplashScreen by remember { mutableStateOf(true) }

    LaunchedEffect(showSplashScreen) {
        if (showSplashScreen) {
            delay(splashScreenDuration)
            showSplashScreen = false
            navController.navigate(Screen.PhotoListScreen.route)
        }
    }


    PhotoViewerTheme(darkTheme = isDarkModeOn.value) {
        Surface(color = MaterialTheme.colors.background) {
            NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
                // Splash Screen
                composable(route = Screen.SplashScreen.route) {
                    SplashScreen(versionName = versionName)
                }
                // Main Content
                composable(route = Screen.PhotoListScreen.route) {
                    PhotoListScreen(navController = navController, versionName = versionName)
                }
                composable(route = Screen.PhotoDetailScreen.route + "/{id}") {
                    PhotoDetailScreen()
                }
                composable(route = Screen.LikedPhotoListScreen.route) {
                    LikedPhotoListScreen(navController = navController)
                }
            }
        }
    }
}