package com.gaffy.breakingbadtechtest.data.repository

import com.gaffy.breakingbadtechtest.API_CHARACTERS
import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface WebApiService{
    @GET(API_CHARACTERS)
    fun getBreakingBadChars(): Single<List<BreakingBadChar>>
}