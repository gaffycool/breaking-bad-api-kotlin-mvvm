package com.gaffy.breakingbadtechtest.data.repository

import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RemoteBreakingBadRepository(private val apiService: WebApiService) : BreakingBadRepository {
    override fun getBreakingBadCharacters(): Single<List<BreakingBadChar>> {
        return apiService.getBreakingBadChars().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}