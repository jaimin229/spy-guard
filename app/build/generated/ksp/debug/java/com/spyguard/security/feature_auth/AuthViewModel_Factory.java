package com.spyguard.security.feature_auth;

import com.spyguard.security.core.camera.IntruderCameraEngine;
import com.spyguard.security.core.datastore.PreferencesManager;
import com.spyguard.security.core.notification.SpyGuardNotificationManager;
import com.spyguard.security.domain.repository.AppLockRepository;
import com.spyguard.security.domain.repository.IntruderRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<AppLockRepository> appLockRepositoryProvider;

  private final Provider<IntruderRepository> intruderRepositoryProvider;

  private final Provider<IntruderCameraEngine> cameraEngineProvider;

  private final Provider<SpyGuardNotificationManager> notificationManagerProvider;

  public AuthViewModel_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<AppLockRepository> appLockRepositoryProvider,
      Provider<IntruderRepository> intruderRepositoryProvider,
      Provider<IntruderCameraEngine> cameraEngineProvider,
      Provider<SpyGuardNotificationManager> notificationManagerProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.appLockRepositoryProvider = appLockRepositoryProvider;
    this.intruderRepositoryProvider = intruderRepositoryProvider;
    this.cameraEngineProvider = cameraEngineProvider;
    this.notificationManagerProvider = notificationManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(preferencesManagerProvider.get(), appLockRepositoryProvider.get(), intruderRepositoryProvider.get(), cameraEngineProvider.get(), notificationManagerProvider.get());
  }

  public static AuthViewModel_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider,
      Provider<AppLockRepository> appLockRepositoryProvider,
      Provider<IntruderRepository> intruderRepositoryProvider,
      Provider<IntruderCameraEngine> cameraEngineProvider,
      Provider<SpyGuardNotificationManager> notificationManagerProvider) {
    return new AuthViewModel_Factory(preferencesManagerProvider, appLockRepositoryProvider, intruderRepositoryProvider, cameraEngineProvider, notificationManagerProvider);
  }

  public static AuthViewModel newInstance(PreferencesManager preferencesManager,
      AppLockRepository appLockRepository, IntruderRepository intruderRepository,
      IntruderCameraEngine cameraEngine, SpyGuardNotificationManager notificationManager) {
    return new AuthViewModel(preferencesManager, appLockRepository, intruderRepository, cameraEngine, notificationManager);
  }
}
