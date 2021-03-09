package com.mun0n.marvelapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mun0n.marvelapp.TestCoroutineRule
import com.mun0n.marvelapp.data.model.CharactersMock
import com.mun0n.marvelapp.model.CharacterResponse
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.network.MarvelApi
import com.mun0n.marvelapp.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var characterObserver: Observer<Resource<CharacterResponse>>

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {

        testCoroutineRule.runBlockingTest {
            doReturn(CharactersMock.CHARACTER_RESPONSE)
                .`when`(mainRepository)
                .loadMarvelCharacters(
                     offset = 0
                )
            val viewModel = MainViewModel(mainRepository)
            viewModel.getCharacters().observeForever(characterObserver)
            verify(mainRepository).loadMarvelCharacters(offset = 0)
            verify(characterObserver).onChanged(Resource.success(CharactersMock.CHARACTER_RESPONSE))
            viewModel.getCharacters().removeObserver(characterObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        val errorMessage = "Error Message For You"
        testCoroutineRule.runBlockingTest {
            doThrow(RuntimeException(errorMessage))
                .`when`(mainRepository)
                .loadMarvelCharacters(
                    offset = 0
                )
            val viewModel = MainViewModel(mainRepository)
            viewModel.getCharacters().observeForever(characterObserver)
            verify(mainRepository).loadMarvelCharacters(offset = 0)
            verify(characterObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.getCharacters().removeObserver(characterObserver)
        }
    }

}