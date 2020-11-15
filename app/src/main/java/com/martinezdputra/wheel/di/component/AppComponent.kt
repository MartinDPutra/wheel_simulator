package com.martinezdputra.wheel.di.component

import android.app.Application
import android.content.Context
import com.martinezdputra.wheel.di.annotation.ApplicationScope
import com.martinezdputra.wheel.di.module.ApiModule
import com.martinezdputra.wheel.di.module.AppModule
import com.martinezdputra.wheel.di.module.ViewModelModule
import com.martinezdputra.wheel.screen.HomepageActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    AppModule::class,
    ViewModelModule::class,
    ApiModule::class
])
@ApplicationScope
interface AppComponent {

    fun getApplicationContext(): Context

    fun getApplication(): Application

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(homepageActivity: HomepageActivity)
}