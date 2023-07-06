package com.magallanes.photoviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PhotoDatabase: RoomDatabase() {
    abstract val photoDao: PhotoDao
}