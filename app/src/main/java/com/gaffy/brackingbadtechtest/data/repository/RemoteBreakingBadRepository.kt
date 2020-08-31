package com.gaffy.brackingbadtechtest.data.repository

import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import io.reactivex.rxjava3.core.Single

class RemoteBreakingBadRepository(private val apiService: WebApiService)
    : BreakingBadRepository {
    override fun getBreakingBadCharacters(): Single<List<BreakingBadChar>> {
        return apiService.getBreakingBadChars()
    }
}