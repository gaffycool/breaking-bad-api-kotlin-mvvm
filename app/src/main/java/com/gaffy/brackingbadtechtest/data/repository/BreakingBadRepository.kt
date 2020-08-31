package com.gaffy.brackingbadtechtest.data.repository

import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import io.reactivex.rxjava3.core.Single

interface BreakingBadRepository {
    fun getBreakingBadCharacters(): Single<List<BreakingBadChar>>
}