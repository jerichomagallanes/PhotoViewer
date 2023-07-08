package com.magallanes.photoviewer.domain.use_case.get_photo_by_id

import com.magallanes.photoviewer.repository.FakePhotoRepository
import com.magallanes.photoviewer.common.CustomException
import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.mocks.GetPhotoByIdMockResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.magallanes.photoviewer.domain.model.get_photo_by_id.PhotoDetail as PhotoDetail1

@ExperimentalCoroutinesApi
class GetPhotoByIdUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var fakeRepository: FakePhotoRepository
    private lateinit var getPhotoByIdUseCase: GetPhotoByIdUseCase

    @Before
    fun setUp() {
        fakeRepository = mockk()
        getPhotoByIdUseCase = GetPhotoByIdUseCase(fakeRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get Photo Details, correct photo id returns success`() = testDispatcher.runBlockingTest {
        val photoId = "2064826"
        val photoDetail = GetPhotoByIdMockResponse.getMockPhotoByIdMockResponse()

        coEvery { fakeRepository.getPhotoById(photoId) } coAnswers { photoDetail }

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(photoDetail, (result as Resource.Success).data)

        job.cancel()
    }

    @Test
    fun `Get Photo Details, repository throws CustomException returns error`() = testDispatcher.runBlockingTest {
        val photoId = "2064826"
        val errorMessage = "An error occurred"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException(errorMessage)

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)

        job.cancel()
    }

    @Test
    fun `Get Photo Details, repository throws generic exception returns error`() = testDispatcher.runBlockingTest {
        val photoId = "2064826"
        val errorMessage = "An error occurred"
        coEvery { fakeRepository.getPhotoById(photoId) } throws Exception(errorMessage)

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            try {
                flow.collect {
                    result = it
                }
            } catch (e: Exception) {
                result = Resource.Error(errorMessage)
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)

        job.cancel()
    }

    @Test
    fun `Get Photo Details, repository returns null photo returns error`() = testDispatcher.runBlockingTest {
        val photoId = "2064826"
        val errorMessage = "Photo not found"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException(errorMessage)

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
        assertNull((result as Resource.Error).data)

        job.cancel()
    }

    @Test
    fun `Get Photo Details, empty photo id returns 404 error`() = testDispatcher.runBlockingTest {
        val photoId = ""

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException("An unexpected error occurred: HTTP 404")

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals("An unexpected error occurred: HTTP 404", (result as Resource.Error).message)

        job.cancel()
    }

    @Test
    fun `Get Photo Details, long photo id returns success`() = testDispatcher.runBlockingTest {
        val photoId = "12345678901234567890"

        val photoDetail = GetPhotoByIdMockResponse.getMockPhotoByIdMockResponse()
        coEvery { fakeRepository.getPhotoById(photoId) } coAnswers { photoDetail }

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(photoDetail, (result as Resource.Success).data)

        job.cancel()
    }

    @Test
    fun `Get Photo Details, special characters in photo id returns 404 error`() = testDispatcher.runBlockingTest {
        val photoId = "photo#123"

        coEvery { fakeRepository.getPhotoById(photoId) } throws CustomException("An unexpected error occurred: HTTP 404")

        val flow = getPhotoByIdUseCase(photoId)
        var result: Resource<PhotoDetail1>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        advanceUntilIdle()

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals("An unexpected error occurred: HTTP 404", (result as Resource.Error).message)

        job.cancel()
    }
}
