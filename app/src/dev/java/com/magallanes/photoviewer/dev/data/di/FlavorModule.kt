package com.magallanes.photoviewer.dev.data.di

import com.magallanes.photoviewer.data.local.PhotoDao
import com.magallanes.photoviewer.domain.repository.PhotoDatabaseRepository
import com.magallanes.photoviewer.domain.repository.PhotoRepository
import com.magallanes.photoviewer.dev.data.repository.FakePhotoDatabaseRepository
import com.magallanes.photoviewer.dev.data.repository.FakePhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlavorModule {

    @Provides
    @Singleton
    fun providePhotoRepository(): PhotoRepository {
        return FakePhotoRepository()
    }

    @Provides
    @Singleton
    fun providePhotoDatabaseRepository(photoDao: PhotoDao): PhotoDatabaseRepository {
        return FakePhotoDatabaseRepository(photoDao = photoDao)
    }

}
