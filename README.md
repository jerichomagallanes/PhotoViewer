# 🌄 Photo Search and Viewer Application

This Android application allows users to search and browse for photos. It is developed using Kotlin and follows the MVVM architecture pattern with Clean Architecture principles, including the UseCases pattern. The app utilizes the Pexels API for retrieving photos.

App Demo

![Demo 1](https://github.com/jerichomagallanes/PhotoViewer/assets/56428317/a213cabf-d563-4263-a9a4-65deb9eea267)

![Demo 2](https://github.com/jerichomagallanes/PhotoViewer/assets/56428317/ad3b373b-f0b7-438d-bf7b-de910b5506fd)

## 📋 Features

- **Photo Search Screen**: Users can enter search keywords to view a list of photos with photographer names.
- **Like/Unlike Photos**: Users can like/unlike a photo.
- **Liked Photos Screen**: This screen will display all the photos liked by the user, allowing them to view their saved photos.
- **Photo Display Screen**: Displays selected photos in full-screen mode.
- **Dark Mode**: Supports Dark Mode for low-light conditions.

## 📚 Libraries Used

- **Kotlin**: Programming language for Android development.
- **Pexels API**: API for retrieving high-quality photos.
- **Kotlin Coroutines**: For multi-threading and asynchronous programming.
- **Kotlin Flow**: For handling asynchronous data streams.
- **Jetpack Compose**: Modern UI toolkit for building native Android UIs declaratively.
- **Coil**: Image loading library for simplifying image display.
- **Moshi**: JSON parsing library.
- **OkHttp Logging Interceptor**: Interceptor for logging HTTP requests and responses.
- **Retrofit**: Type-safe HTTP client.
- **Dagger**: Dependency injection framework.
- **Hilt Navigation Compose**: Navigation support library for Jetpack Compose apps using Hilt.
- **Navigation Compose**: Navigation support library for Jetpack Compose apps.
- **Lifecycle Compose**: Lifecycle-aware Jetpack Compose components.
- **Material**: Material Design components and resources for Android.
- **Room**: SQLite database library for data persistence.
- **Shared Preferences**: Data Persistence.
- **GSON**: JSON serialization/deserialization library.

## 🚀 Getting Started

1. Clone the repository from GitHub: [repository-link](https://github.com/jerichomagallanes/PhotoViewer.git).
2. Obtain an API key from Pexels: [Pexels API](https://www.pexels.com/api/documentation/).
3. Navigate to the root folder of the project and open the file named 'app.properties' with a text editor.
4. Replace the placeholder 'PLACE_YOUR_API_KEY_HERE' with your actual API Key.
5. Open the project in Android Studio.
6. Build and run the application on an Android device or emulator.

## 💻 Development

### 🛠️ Build Variants

This project has different build variants to enhance flexibility based on specific requirements. The variants include:

   - Dev: This variant is primarily for developers and does not connect to a real API. It uses Fake Repositories containing dummy responses hardcoded by developers.

   - SIT: This variant is intended to be injected with a different API specifically designed for System Integration Testing (SIT) purposes. It allows for thorough testing and validation of the application's integration with the SIT environment.

   - Prod: This variant uses the actual API intended for the production environment.

   There are 2 types for every variant which are dev and debug (devDebug, devRelease, sitDebug, sitRelease, prodDebug, prodRelease). The dev type is used to compile and debug the application on Android Studio, whereas the release type cannot be compiled and can only be built by generating a signed APK/Bundle using a signing key. Each variant has its own app signing keys, allowing them to be deployed on the Google Play Store independently.
