package com.magallanes.photoviewer.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.magallanes.photoviewer.common.Constants
import com.magallanes.photoviewer.data.local.PhotoDao
import com.magallanes.photoviewer.data.local.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.KEY_SHARED_PREF, Context.MODE_PRIVATE)
    }
}
