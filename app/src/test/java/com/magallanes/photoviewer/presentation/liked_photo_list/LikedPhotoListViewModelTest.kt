package com.magallanes.photoviewer.presentation.liked_photo_list

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.domain.model.get_search_photos.PhotoSrc
import com.magallanes.photoviewer.repository.FakePhotoDatabaseRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LikedPhotoListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `likePhoto should insert liked photo and refresh list`() = testDispatcher.runBlockingTest {
        Dispatchers.setMain(testDispatcher)

        val photoListDatabaseRepository = mockk<FakePhotoDatabaseRepository>(relaxed = true)
        val viewModel = LikedPhotoListViewModel(photoListDatabaseRepository)

        val photo = Photo(
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
        )
        val likedPhotos = listOf(photo)

        coEvery {
            photoListDatabaseRepository.insertLikedPhoto(photo)
        } coAnswers {
            coEvery {
                photoListDatabaseRepository.getAllLikedPhotos()
            }.answers {
                flowOf(likedPhotos)
            }
        }

        viewModel.likePhoto(photo)

        val emittedPhotos = viewModel.state.value.likedPhotos.toList().flatten()

        coVerify {
            photoListDatabaseRepository.insertLikedPhoto(photo)
            photoListDatabaseRepository.getAllLikedPhotos()
        }
        assertEquals(likedPhotos, emittedPhotos)
        assertEquals(0, viewModel.state.value.error.length)
    }

    @Test
    fun `unlikePhoto should delete liked photo and refresh list`() = testDispatcher.runBlockingTest {
        Dispatchers.setMain(testDispatcher)

        val photoListDatabaseRepository = mockk<FakePhotoDatabaseRepository>(relaxed = true)
        val viewModel = LikedPhotoListViewModel(photoListDatabaseRepository)

        val photo = Photo(
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
                large = "https://images.pexels.com/photos/1250643/pexels-photo-1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                large2x = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                medium = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&h=350",
                original = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg",
                portrait = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                small = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&h=130",
                tiny = "https://images.pexels.com/photos/1250643/pexels-photo-1250643.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
            )
        )
        val likedPhotos = emptyList<Photo>()

        coEvery {
            photoListDatabaseRepository.deleteLikedPhoto(photo)
        } coAnswers {
            coEvery {
                photoListDatabaseRepository.getAllLikedPhotos()
            } coAnswers {
                flowOf(likedPhotos)
            }
        }

        viewModel.unlikePhoto(photo)

        coVerify {
            photoListDatabaseRepository.deleteLikedPhoto(photo)
            photoListDatabaseRepository.getAllLikedPhotos()
        }

        viewModel.state.value.likedPhotos.collect { collectedLikedPhotos ->
            assertEquals(likedPhotos, collectedLikedPhotos)
        }
        assertEquals("You have no liked photos", viewModel.state.value.error)
    }
}
