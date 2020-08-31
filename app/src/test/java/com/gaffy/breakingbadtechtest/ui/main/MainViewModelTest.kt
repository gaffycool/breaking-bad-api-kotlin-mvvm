package com.gaffy.breakingbadtechtest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import com.gaffy.breakingbadtechtest.data.repository.BreakingBadRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.lang.RuntimeException

@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {
    lateinit var subject: MainViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    @MockK
    lateinit var mockRepository: BreakingBadRepository

    @MockK()
    lateinit var observer: Observer<MainViewModel.ViewState>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        subject = MainViewModel(mockRepository)
        subject.state.observeForever(observer)
        every { observer.onChanged(any()) } returns Unit
    }

    @Test
    fun `when repository returns non empty list of data, state should be set to success with list of characters`() {
        //When
        val data = listOf(BreakingBadChar(name = "Demo"))
        every { mockRepository.getBreakingBadCharacters() } returns Single.just(data)

        //Then
        subject.getBreakingBadChars()

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Success(data)) }
    }

    @Test
    fun `when repository returns non empty list of data, state should be set to success with list of characters filtered by season if season is non zero`() {
        //When
        val data = listOf(BreakingBadChar(name = "Demo", appearance = listOf(1)),
            BreakingBadChar(name = "Demo2", appearance = listOf(2)))
        val expectedData = listOf(BreakingBadChar(name = "Demo2", appearance = listOf(2)))
        every { mockRepository.getBreakingBadCharacters() } returns Single.just(data)

        //Then
        subject.getBreakingBadChars(season = 2)

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Success(expectedData)) }
    }

    @Test
    fun `when repository returns non empty list of data, state should be set to success with list of characters filtered by characterName if characterName is non empty`() {
        //When
        val data = listOf(BreakingBadChar(name = "Demo", appearance = listOf(1)),
            BreakingBadChar(name = "Demo2", appearance = listOf(2)))
        val expectedData = listOf(BreakingBadChar(name = "Demo2", appearance = listOf(2)))
        every { mockRepository.getBreakingBadCharacters() } returns Single.just(data)

        //Then
        subject.getBreakingBadChars(characterName = "Demo2")

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Success(expectedData)) }
    }

    @Test
    fun `when repository returns non empty list of data, state should be set to success with list of characters filtered by characterName if characterName is non empty and then by Season number if season is non zero`() {
        //When
        val data = listOf(BreakingBadChar(name = "Demo", appearance = listOf(1)),
            BreakingBadChar(name = "Demo2", appearance = listOf(2)),
            BreakingBadChar(name = "Demo32", appearance = listOf(2)),
            BreakingBadChar(name = "Demo23", appearance = listOf(3)))
        val expectedData = listOf(BreakingBadChar(name = "Demo2", appearance = listOf(2)))
        every { mockRepository.getBreakingBadCharacters() } returns Single.just(data)

        //Then
        subject.getBreakingBadChars(characterName = "Demo2", season = 2)

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Success(expectedData)) }
    }
    @Test
    fun `when repository returns empty list of data, state should be set to error with List is Empty message`() {
        //When
        val data = emptyList<BreakingBadChar>()
        every { mockRepository.getBreakingBadCharacters() } returns Single.just(data)

        //Then
        subject.getBreakingBadChars()

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Error("No Result Found")) }
    }
    @Test
    fun `when repository returns failure with localized message, state should be set to error with localized message`() {
        //When
        val message = "Error Message"
        val data = RuntimeException(message)
        every { mockRepository.getBreakingBadCharacters() } returns Single.error(data)

        //Then
        subject.getBreakingBadChars()

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Error(message)) }
    }

    @Test
    fun `when repository returns failure with no message, state should be set to error with Unknown Error message`() {
        //When
        val data = RuntimeException()
        every { mockRepository.getBreakingBadCharacters() } returns Single.error(data)

        //Then
        subject.getBreakingBadChars()

        //Verify
        verify { observer.onChanged(MainViewModel.ViewState.InProgress) }
        verify { observer.onChanged(MainViewModel.ViewState.Error("Unknown Error")) }
    }
}