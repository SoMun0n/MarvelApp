package com.mun0n.marvelapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mun0n.marvelapp.BuildConfig
import com.mun0n.marvelapp.TestCoroutineRule
import com.mun0n.marvelapp.data.model.CharacterMock
import com.mun0n.marvelapp.data.model.CharactersMock
import com.mun0n.marvelapp.network.MarvelApi
import com.mun0n.marvelapp.util.md5
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var marvelApi: MarvelApi

    private val options = mapOf<String, String>(
        "ts" to "1",
        "apikey" to BuildConfig.MARVEL_PUBLIC_KEY,
        "hash" to "1${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}".md5()
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(CharacterMock.CHARACTER_RESPONSE)
                .`when`(marvelApi)
                .fetchMarvelSingleCharacter(
                    0,
                    options
                )
            val detailRepository = DetailRepository(marvelApi)
            val response = detailRepository.loadMarvelCharacter(0)
            Mockito.verify(marvelApi).fetchMarvelSingleCharacter(0, options)
            Assert.assertEquals(response, CharacterMock.CHARACTER_RESPONSE)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        val errorMessage = "Error Message For You"
        testCoroutineRule.runBlockingTest {
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(marvelApi)
                .fetchMarvelSingleCharacter(
                    0,
                    options
                )
            val detailRepository = DetailRepository(marvelApi)
            val response = detailRepository.loadMarvelCharacter(0)
            Mockito.verify(marvelApi).fetchMarvelSingleCharacter(0, options)
            Assert.assertNull(response)
        }
    }
}