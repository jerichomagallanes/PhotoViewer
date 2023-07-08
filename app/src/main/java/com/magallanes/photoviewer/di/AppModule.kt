package com.magallanes.photoviewer.di

import android.content.Context
import androidx.room.Room
import com.magallanes.photoviewer.BuildConfig
import com.magallanes.photoviewer.common.Constants
import com.magallanes.photoviewer.data.local.PhotoDao
import com.magallanes.photoviewer.data.local.PhotoDatabase
import com.magallanes.photoviewer.data.remote.PexelsApi
import com.magallanes.photoviewer.data.repository.PhotoDatabaseRepositoryImpl
import com.magallanes.photoviewer.data.repository.PhotoRepositoryImpl
import com.magallanes.photoviewer.domain.repository.PhotoDatabaseRepository
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePhotoDatabase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photos.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(database: PhotoDatabase): PhotoDao {
        return database.photoDao
    }

    @Provides
    @Singleton
    fun providePexelsApi(): PexelsApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", BuildConfig.PEXELS_API_KEY)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()

        val moshi = Moshi.Builder().build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(PexelsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: PexelsApi): PhotoRepository {
        return PhotoRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun providePhotoDatabaseRepository(photoDao: PhotoDao): PhotoDatabaseRepository {
        return PhotoDatabaseRepositoryImpl(photoDao = photoDao)
    }
}