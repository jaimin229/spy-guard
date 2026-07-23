package com.spyguard.security.data.repository;

import com.spyguard.security.core.database.dao.AppLockLogDao;
import com.spyguard.security.core.database.dao.LockedAppDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppLockRepositoryImpl_Factory implements Factory<AppLockRepositoryImpl> {
  private final Provider<LockedAppDao> lockedAppDaoProvider;

  private final Provider<AppLockLogDao> appLockLogDaoProvider;

  public AppLockRepositoryImpl_Factory(Provider<LockedAppDao> lockedAppDaoProvider,
      Provider<AppLockLogDao> appLockLogDaoProvider) {
    this.lockedAppDaoProvider = lockedAppDaoProvider;
    this.appLockLogDaoProvider = appLockLogDaoProvider;
  }

  @Override
  public AppLockRepositoryImpl get() {
    return newInstance(lockedAppDaoProvider.get(), appLockLogDaoProvider.get());
  }

  public static AppLockRepositoryImpl_Factory create(Provider<LockedAppDao> lockedAppDaoProvider,
      Provider<AppLockLogDao> appLockLogDaoProvider) {
    return new AppLockRepositoryImpl_Factory(lockedAppDaoProvider, appLockLogDaoProvider);
  }

  public static AppLockRepositoryImpl newInstance(LockedAppDao lockedAppDao,
      AppLockLogDao appLockLogDao) {
    return new AppLockRepositoryImpl(lockedAppDao, appLockLogDao);
  }
}
