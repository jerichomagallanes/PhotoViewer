package com.magallanes.photoviewer.mocks

import com.google.gson.Gson
import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.PhotoDetailDto
import com.magallanes.photoviewer.data.remote.dto.get_photo_by_id.toPhotoDetail
import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail

object GetPhotoDetailMocker {
    private val gson = Gson()

    fun createPhotoDetail(): PhotoDetail {
        val jsonString = """
            {
                "id": 2064826,
                "width": 2736,
                "height": 4104,
                "url": "https://www.pexels.com/photo/man-wearing-brown-hoodie-2064826/",
                "photographer": "Davi Pimentel",
                "photographerUrl": "https://www.pexels.com/@davifnr",
                "photographerId": 1057139,
                "alt": "Man Wearing Brown Hoodie",
                "src": {
                    "landscape": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
                    "large": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                    "large2x": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                    "medium": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&h=350",
                    "original": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg",
                    "portrait": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                    "small": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&h=130",
                    "tiny": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
                }
            }
        """.trimIndent()

        return gson.fromJson(jsonString, PhotoDetailDto::class.java).toPhotoDetail()
    }
}
