package com.gaffy.breakingbadtechtest

import android.app.Application
import com.gaffy.breakingbadtechtest.data.repository.BreakingBadRepository
import com.gaffy.breakingbadtechtest.data.repository.RemoteBreakingBadRepository
import com.gaffy.breakingbadtechtest.data.repository.WebApiService
import com.gaffy.breakingbadtechtest.ui.main.MainViewModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {
    val appModule = module {

        single<WebApiService> {
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(WebApiService::class.java)
        }

        single<BreakingBadRepository> { RemoteBreakingBadRepository(get()) }
        viewModel { MainViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(level = Level.ERROR)
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}