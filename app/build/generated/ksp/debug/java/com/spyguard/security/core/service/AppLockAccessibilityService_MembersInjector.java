package com.spyguard.security.core.service;

import com.spyguard.security.core.database.dao.LockedAppDao;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppLockAccessibilityService_MembersInjector implements MembersInjector<AppLockAccessibilityService> {
  private final Provider<LockedAppDao> lockedAppDaoProvider;

  public AppLockAccessibilityService_MembersInjector(Provider<LockedAppDao> lockedAppDaoProvider) {
    this.lockedAppDaoProvider = lockedAppDaoProvider;
  }

  public static MembersInjector<AppLockAccessibilityService> create(
      Provider<LockedAppDao> lockedAppDaoProvider) {
    return new AppLockAccessibilityService_MembersInjector(lockedAppDaoProvider);
  }

  @Override
  public void injectMembers(AppLockAccessibilityService instance) {
    injectLockedAppDao(instance, lockedAppDaoProvider.get());
  }

  @InjectedFieldSignature("com.spyguard.security.core.service.AppLockAccessibilityService.lockedAppDao")
  public static void injectLockedAppDao(AppLockAccessibilityService instance,
      LockedAppDao lockedAppDao) {
    instance.lockedAppDao = lockedAppDao;
  }
}
