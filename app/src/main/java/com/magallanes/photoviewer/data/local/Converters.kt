package com.magallanes.photoviewer.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.magallanes.photoviewer.domain.model.get_search_photos.PhotoSrc

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromPhotoSrc(photoSrc: PhotoSrc): String {
        return gson.toJson(photoSrc)
    }

    @TypeConverter
    fun toPhotoSrc(photoSrcJson: String): PhotoSrc {
        return gson.fromJson(photoSrcJson, PhotoSrc::class.java)
    }
}
