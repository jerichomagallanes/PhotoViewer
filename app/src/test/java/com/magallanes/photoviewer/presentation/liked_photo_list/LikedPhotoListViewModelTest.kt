package com.magallanes.photoviewer.presentation.liked_photo_list

import com.magallanes.photoviewer.domain.model.get_search_photos.Photo
import com.magallanes.photoviewer.mocks.GetPhotoMocker
import com.magallanes.photoviewer.repository.FakePhotoDatabaseRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LikedPhotoListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var photoListDatabaseRepository: FakePhotoDatabaseRepository
    private lateinit var viewModel: LikedPhotoListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        photoListDatabaseRepository = mockk(relaxed = true)
        viewModel = LikedPhotoListViewModel(photoListDatabaseRepository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `likePhoto should insert liked photo and refresh list`() = runBlocking(testDispatcher) {
        val photo = GetPhotoMocker.createPhoto()
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
    fun `unlikePhoto should delete liked photo and refresh list`() = runBlocking(testDispatcher) {
        val photo = GetPhotoMocker.createPhoto()
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
