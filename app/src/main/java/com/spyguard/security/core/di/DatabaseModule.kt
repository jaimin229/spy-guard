package com.spyguard.security.core.di

import android.content.Context
import androidx.room.Room
import com.spyguard.security.core.database.SpyGuardDatabase
import com.spyguard.security.core.database.dao.AppLockLogDao
import com.spyguard.security.core.database.dao.IntruderLogDao
import com.spyguard.security.core.database.dao.LockedAppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SpyGuardDatabase {
        return Room.databaseBuilder(
            context,
            SpyGuardDatabase::class.java,
            "spy_guard_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLockedAppDao(db: SpyGuardDatabase): LockedAppDao = db.lockedAppDao()

    @Provides
    fun provideIntruderLogDao(db: SpyGuardDatabase): IntruderLogDao = db.intruderLogDao()

    @Provides
    fun provideAppLockLogDao(db: SpyGuardDatabase): AppLockLogDao = db.appLockLogDao()
}
