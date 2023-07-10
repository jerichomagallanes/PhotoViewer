package com.magallanes.photoviewer.data.local

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo

class PhotoMapper {
    fun mapToEntity(photo: Photo): PhotoEntity {
        return with(photo) {
            PhotoEntity(
                id = id,
                width = width,
                height = height,
                url = url,
                photographer = photographer,
                photographerUrl = photographerUrl,
                photographerId = photographerId,
                alt = alt,
                src = src
            )
        }
    }

    fun mapToDomain(photoEntity: PhotoEntity): Photo {
        return with(photoEntity) {
            Photo(
                id = id,
                width = width,
                height = height,
                url = url,
                photographer = photographer,
                photographerUrl = photographerUrl ?: "",
                photographerId = photographerId,
                alt = alt,
                src = src
            )
        }
    }
}
