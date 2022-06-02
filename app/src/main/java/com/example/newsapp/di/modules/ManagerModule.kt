package com.example.newsapp.di.modules

import com.example.newsapp.shared.navigation.ActivityNavigationManager
import com.example.newsapp.shared.navigation.ActivityNavigationManagerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ManagerModule {

    @Binds
    abstract fun provideActivityNavigationManager(
        activityNavigationManager: ActivityNavigationManagerImpl
    ): ActivityNavigationManager

}
