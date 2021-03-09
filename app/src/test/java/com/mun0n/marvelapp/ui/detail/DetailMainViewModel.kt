package com.mun0n.marvelapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mun0n.marvelapp.TestCoroutineRule
import com.mun0n.marvelapp.data.model.CharacterMock
import com.mun0n.marvelapp.data.model.CharactersMock
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.model.SingleCharacterResponse
import com.mun0n.marvelapp.network.MarvelApi
import com.mun0n.marvelapp.repository.DetailRepository
import com.mun0n.marvelapp.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class DetailMainViewModel {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var marvelApi: MarvelApi

    @Mock
    private lateinit var detailRepository: DetailRepository

    @Mock
    private lateinit var characterObserver: Observer<Resource<SingleCharacterResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {

        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(CharacterMock.CHARACTER_RESPONSE)
                .`when`(detailRepository)
                .loadMarvelCharacter(
                    id = 0
                )
            val viewModel = DetailViewModel(detailRepository, "0")
            viewModel.getCharacterData().observeForever(characterObserver)
            Mockito.verify(detailRepository).loadMarvelCharacter(id = 0)
            Mockito.verify(characterObserver).onChanged(Resource.success(CharacterMock.CHARACTER_RESPONSE))
            viewModel.getCharacterData().removeObserver(characterObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        val errorMessage = "Error Message For You"
        testCoroutineRule.runBlockingTest {
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(detailRepository)
                .loadMarvelCharacter(
                    id = 0
                )
            val viewModel = DetailViewModel(detailRepository, "0")
            viewModel.getCharacterData().observeForever(characterObserver)
            Mockito.verify(detailRepository).loadMarvelCharacter(id = 0)
            Mockito.verify(characterObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.getCharacterData().removeObserver(characterObserver)
        }
    }
}