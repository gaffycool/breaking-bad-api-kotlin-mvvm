package com.gaffy.brackingbadtechtest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import com.gaffy.brackingbadtechtest.data.repository.BreakingBadRepository
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
    fun `when repository returns failure, state should be set to error with error message`() {
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
}