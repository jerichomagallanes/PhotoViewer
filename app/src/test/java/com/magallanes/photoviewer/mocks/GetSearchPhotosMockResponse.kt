package com.magallanes.photoviewer.mocks

import com.google.gson.Gson
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos

object GetSearchPhotosMockResponse {
    private val gson = Gson()

    fun getSearchPhotosMockResponse(): SearchPhotos {
        val jsonString = """
        {
          "page": 1,
          "per_page": 15,
          "photos": [
            {
              "id": 2064826,
              "width": 2736,
              "height": 4104,
              "url": "https://www.pexels.com/photo/man-wearing-brown-hoodie-2064826/",
              "photographer": "Davi Pimentel",
              "photographer_url": "https://www.pexels.com/@davifnr",
              "photographer_id": 1057139,
              "avg_color": "#5B5751",
              "src": {
                "original": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg",
                "large2x": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "large": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "medium": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&h=350",
                "small": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&h=130",
                "portrait": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                "landscape": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
                "tiny": "https://images.pexels.com/photos/2064826/pexels-photo-2064826.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
              },
              "liked": false,
              "alt": "Man Wearing Brown Hoodie"
            }
          ],
          "total_results": 8000,
          "next_page": "https://api.pexels.com/v1/search/?page=2&per_page=15&query=people"
        }
        """.trimIndent()

        return gson.fromJson(jsonString, SearchPhotos::class.java)

    }
}
