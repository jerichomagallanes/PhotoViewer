package com.magallanes.photoviewer.presentation.photo_detail

import androidx.lifecycle.SavedStateHandle
import com.magallanes.photoviewer.common.Constants
import com.magallanes.photoviewer.common.CustomException
import com.magallanes.photoviewer.domain.use_case.get_photo_by_id.GetPhotoByIdUseCase
import com.magallanes.photoviewer.dev.data.mocks.GetPhotoDetailMocker
import com.magallanes.photoviewer.dev.data.repository.FakePhotoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoDetailViewModelTest {
    private lateinit var viewModel: PhotoDetailViewModel
    private lateinit var getPhotoByIdUseCase: GetPhotoByIdUseCase
    private lateinit var fakeRepository: FakePhotoRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = mockk(relaxed = true)
        getPhotoByIdUseCase = GetPhotoByIdUseCase(fakeRepository)
        savedStateHandle = SavedStateHandle().apply {
            set(Constants.PARAM_PHOTO_ID, "123")
        }
        viewModel = PhotoDetailViewModel(getPhotoByIdUseCase, savedStateHandle)
    }

    @Test
    fun `getPhotoById should update state with photo detail when successful`() = runBlocking(testDispatcher) {

        val photoId = "123"
        val photoDetail = GetPhotoDetailMocker.createPhotoDetail()

        coEvery { fakeRepository.getPhotoById(photoId) } coAnswers { photoDetail }

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertNotNull(state.photoDetail)
        assertEquals(photoDetail, state.photoDetail)
    }

    @Test
    fun `getPhotoById should update state with error message when repository throws a custom exception`() = runBlocking(testDispatcher) {

        val photoId = "123"
        val errorMessage = "An error occurred"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException(errorMessage)

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertTrue(state.error.isNotBlank())
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `Get Photo Details, repository throws generic exception updates state with error message`() = runBlocking(testDispatcher) {

        val photoId = "2064826"
        val errorMessage = "An error occurred"

        coEvery { fakeRepository.getPhotoById(photoId) } coAnswers { throw Exception(errorMessage) }

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertNull(state.photoDetail)
        assertTrue(state.error.isNotBlank())
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `Get Photo Details, repository returns null photo updates state with error message`() = runBlocking(testDispatcher) {

        val photoId = "2064826"
        val errorMessage = "Photo not found"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException(errorMessage)

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertNull(state.photoDetail)
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `Get Photo Details, empty photo id updates state with error message`() = runBlocking(testDispatcher) {

        val photoId = ""
        val errorMessage = "An unexpected error occurred: HTTP 404"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException(errorMessage)

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertNull(state.photoDetail)
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `Get Photo Details, long photo id updates state with photo detail`() = runBlocking(testDispatcher) {

        val photoId = "12345678901234567890"
        val photoDetail = GetPhotoDetailMocker.createPhotoDetail()

        coEvery { fakeRepository.getPhotoById(photoId) } coAnswers { photoDetail }

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertNotNull(state.photoDetail)
        assertEquals(photoDetail, state.photoDetail)
    }

    @Test
    fun `Get Photo Details, special characters in photo id updates state with error message`() = runBlocking(testDispatcher) {

        val photoId = "photo#123"
        val errorMessage = "An unexpected error occurred: HTTP 404"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException(errorMessage)

        viewModel.getPhotoById(photoId)

        val state = viewModel.state.value
        assertNull(state.photoDetail)
        assertEquals(errorMessage, state.error)
    }

}