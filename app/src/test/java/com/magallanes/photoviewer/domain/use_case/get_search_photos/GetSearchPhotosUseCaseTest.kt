package com.magallanes.photoviewer.domain.use_case.get_search_photos

import com.magallanes.photoviewer.common.CustomException
import com.magallanes.photoviewer.common.Resource
import com.magallanes.photoviewer.domain.model.get_search_photos.SearchPhotos
import com.magallanes.photoviewer.mocks.GetSearchPhotosMockResponse
import com.magallanes.photoviewer.repository.FakePhotoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSearchPhotosUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var fakeRepository: FakePhotoRepository
    private lateinit var getSearchPhotosUseCase: GetSearchPhotosUseCase

    @Before
    fun setUp() {
        fakeRepository = mockk()
        getSearchPhotosUseCase = GetSearchPhotosUseCase(fakeRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get Search Photos, repository returns search photos`() = runBlocking(testDispatcher) {

        val query = "nature"
        val searchPhotos = GetSearchPhotosMockResponse.getSearchPhotosMockResponse()

        coEvery { fakeRepository.getSearchPhotos(query) } coAnswers { searchPhotos }

        val flow = getSearchPhotosUseCase(query)
        var result: Resource<SearchPhotos>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(searchPhotos, (result as Resource.Success).data)

        job.cancel()
    }

    @Test
    fun `Get Search Photos, repository throws CustomException returns error`() =
        runBlocking(testDispatcher) {

            val query = "nature"
            val errorMessage = "An error occurred"

            coEvery { fakeRepository.getSearchPhotos(query) } throws CustomException(errorMessage)

            val flow = getSearchPhotosUseCase(query)
            var result: Resource<SearchPhotos>? = null
            val job = launch {
                flow.collect {
                    result = it
                }
            }

            assertNotNull(result)
            assertTrue(result is Resource.Error)
            assertEquals(errorMessage, (result as Resource.Error).message)

            job.cancel()
        }

    @Test
    fun `Get Search Photos, repository returns empty list returns success`() = runBlocking(testDispatcher) {

        val query = "nature"
        coEvery { fakeRepository.getSearchPhotos(query) } returns SearchPhotos(emptyList(), 0)

        val flow = getSearchPhotosUseCase(query)
        var result: Resource<SearchPhotos>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(0, (result as Resource.Success).data?.photos?.size)

        job.cancel()
    }

    @Test
    fun `Get Search Photos, repository throws generic exception returns error`() = runBlocking(testDispatcher) {

        val query = "nature"
        val errorMessage = "An error occurred"
        coEvery { fakeRepository.getSearchPhotos(query) } throws Exception(errorMessage)

        val flow = getSearchPhotosUseCase(query)
        var result: Resource<SearchPhotos>? = null
        val job = launch {
            try {
                flow.collect {
                    result = it
                }
            } catch (e: Exception) {
                result = Resource.Error(errorMessage)
            }
        }

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)

        job.cancel()
    }

    @Test
    fun `Get Search Photos, query returns 400 error`() = runBlocking(testDispatcher) {
        val query = ""

        coEvery { fakeRepository.getSearchPhotos(query) } throws CustomException("An unexpected error occurred: HTTP 400")

        val flow = getSearchPhotosUseCase(query)
        var result: Resource<SearchPhotos>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals("An unexpected error occurred: HTTP 400", (result as Resource.Error).message)

        job.cancel()
    }

    @Test
    fun `Get Search Photos, empty query returns invalid JSON`() = runBlocking(testDispatcher) {
        val query = "eifrwbjlsvbd"

        coEvery { fakeRepository.getSearchPhotos(query) } throws CustomException("Invalid response data")

        val flow = getSearchPhotosUseCase(query)
        var result: Resource<SearchPhotos>? = null
        val job = launch {
            flow.collect {
                result = it
            }
        }

        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals("Invalid response data", (result as Resource.Error).message)

        job.cancel()
    }

}