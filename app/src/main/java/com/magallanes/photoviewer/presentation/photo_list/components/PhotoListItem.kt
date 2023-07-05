package com.magallanes.photoviewer.presentation.photo_list.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.domain.model.get_search_photos.PhotoSrc
import com.magallanes.photoviewer.presentation.ui.theme.PhotoViewerTheme
import androidx.compose.ui.platform.LocalContext

@Composable
fun PhotoItem(
    photo: Photo,
    onItemClick: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(photo) }
                    .aspectRatio(1.5f)
            ) {
                AsyncImage(
                    model = photo.photoSrc.large2x,
                    contentDescription = photo.alt,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = photo.photographerUrl,
                            contentDescription = photo.photographerId.toString(),
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = photo.photographer,
                            fontSize = 8.sp,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PhotoItemPreview() {
    val context = LocalContext.current // Access the context

    PhotoViewerTheme {
        PhotoItem(
            photo = Photo(
                id = 1181424,
                width = 4016,
                height = 6016,
                url = "https://www.pexels.com/photo/woman-smiling-and-holding-teal-book-1181424/",
                photographer = "Christina Morillo",
                photographerUrl = "https://www.pexels.com/@divinetechygirl",
                photographerId = 473730,
                alt = "Woman Smiling and Holding Teal Book",
                photoSrc = PhotoSrc(
                    landscape = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
                    large = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                    large2x = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                    medium = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&h=350",
                    original = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg",
                    portrait = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                    small = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&h=130",
                    tiny = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
                )
            ),
            onItemClick = { clickedPhoto ->
                // Handle item click here, e.g., show a toast message
                Toast.makeText(context, "Item clicked: ${clickedPhoto.id}", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}