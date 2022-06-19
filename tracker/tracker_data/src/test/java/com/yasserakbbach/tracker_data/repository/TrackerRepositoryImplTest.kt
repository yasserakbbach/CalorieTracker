package com.yasserakbbach.tracker_data.repository

import com.google.common.truth.Truth.assertThat
import com.yasserakbbach.tracker_data.remote.OpenFoodApi
import com.yasserakbbach.tracker_data.remote.malformedFoodResponse
import com.yasserakbbach.tracker_data.remote.validFoodResponse
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class TrackerRepositoryImplTest {

    private lateinit var repository: TrackerRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var openFoodApi: OpenFoodApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        openFoodApi = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()
        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = openFoodApi,
        )
    }

    @Test
    fun `Search food, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(validFoodResponse)
        )
        val results = repository.searchFood("banana", 1, 40)

        assertThat(results.isSuccess).isTrue()
    }

    @Test
    fun `Search food, invalid response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
                .setBody(validFoodResponse)
        )
        val results = repository.searchFood("banana", 1, 40)

        assertThat(results.isFailure).isTrue()
    }

    @Test
    fun `Search food, malformed response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setBody(malformedFoodResponse)
        )
        val results = repository.searchFood("banana", 1, 40)

        assertThat(results.isFailure).isTrue()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        clearAllMocks()
    }
}