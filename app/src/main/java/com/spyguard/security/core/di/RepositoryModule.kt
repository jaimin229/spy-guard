package com.spyguard.security.core.di

import com.spyguard.security.data.repository.AppLockRepositoryImpl
import com.spyguard.security.data.repository.IntruderRepositoryImpl
import com.spyguard.security.domain.repository.AppLockRepository
import com.spyguard.security.domain.repository.IntruderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppLockRepository(impl: AppLockRepositoryImpl): AppLockRepository

    @Binds
    @Singleton
    abstract fun bindIntruderRepository(impl: IntruderRepositoryImpl): IntruderRepository
}
