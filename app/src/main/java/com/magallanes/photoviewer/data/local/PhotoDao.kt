package com.magallanes.photoviewer.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PhotoDao {
    @Insert
    suspend fun insertPhoto(photo: PhotoEntity)

    @Update
    suspend fun updatePhoto(photo: PhotoEntity)

    @Delete
    suspend fun deletePhoto(photo: PhotoEntity)

    @Query("SELECT * FROM photoentity")
    fun getAllPhotos(): Flow<List<PhotoEntity>>

}