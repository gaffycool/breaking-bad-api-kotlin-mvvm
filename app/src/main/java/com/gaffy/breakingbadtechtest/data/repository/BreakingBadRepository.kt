package com.gaffy.breakingbadtechtest.data.repository

import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import io.reactivex.rxjava3.core.Single

interface BreakingBadRepository {
    fun getBreakingBadCharacters(): Single<List<BreakingBadChar>>
}