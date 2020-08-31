package com.gaffy.breakingbadtechtest.data.repository

import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class RemoteBreakingBadRepositoryTest {

    lateinit var subject: RemoteBreakingBadRepository

    @MockK
    lateinit var mockApiService: WebApiService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        subject = RemoteBreakingBadRepository(mockApiService)
    }

    @Test
    fun `when ApiService returns single, and getBreakingBadChar called, same single should be returned by the repository`() {
        //When
        val expectedResult = Single.just(emptyList<BreakingBadChar>())
        every { mockApiService.getBreakingBadChars() } returns expectedResult

        //Then
        val actualResult = subject.getBreakingBadCharacters()
        //Verify
        assert(actualResult == expectedResult)
    }
}