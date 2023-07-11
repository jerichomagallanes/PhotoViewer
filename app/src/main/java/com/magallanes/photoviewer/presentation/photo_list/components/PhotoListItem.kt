package com.magallanes.photoviewer.presentation.photo_list.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.res.painterResource
import com.magallanes.photoviewer.R

@Composable
fun PhotoItem(
    photo: Photo,
    onItemClick: (Photo) -> Unit,
    isLiked: Boolean,
    onLikeClick: (Boolean) -> Unit,
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
                    model = photo.src.large2x,
                    contentDescription = photo.alt,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onLikeClick(!isLiked) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                        ),
                        contentDescription = if (isLiked) "Liked" else "Not Liked",
                        tint = if (isLiked) MaterialTheme.colors.primary else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = photo.photographer,
                    fontSize = 8.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .wrapContentWidth(align = Alignment.End)
                )
            }
        }
    }
}

@Preview
@Composable
fun PhotoItemPreview() {
    val context = LocalContext.current

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
                src = PhotoSrc(
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
            isLiked = true,
            onLikeClick = {
                // Handle item click here, e.g., show a toast message
                Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}