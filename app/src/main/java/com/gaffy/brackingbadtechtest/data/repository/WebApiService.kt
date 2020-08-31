package com.gaffy.brackingbadtechtest.data.repository

import com.gaffy.brackingbadtechtest.API_CHARACTERS
import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface WebApiService{
    @GET(API_CHARACTERS)
    fun getBreakingBadChars(): Single<List<BreakingBadChar>>
}