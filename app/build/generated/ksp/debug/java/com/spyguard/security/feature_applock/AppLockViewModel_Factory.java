package com.spyguard.security.feature_applock;

import com.spyguard.security.domain.repository.AppLockRepository;
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
public final class AppLockViewModel_Factory implements Factory<AppLockViewModel> {
  private final Provider<AppLockRepository> appLockRepositoryProvider;

  public AppLockViewModel_Factory(Provider<AppLockRepository> appLockRepositoryProvider) {
    this.appLockRepositoryProvider = appLockRepositoryProvider;
  }

  @Override
  public AppLockViewModel get() {
    return newInstance(appLockRepositoryProvider.get());
  }

  public static AppLockViewModel_Factory create(
      Provider<AppLockRepository> appLockRepositoryProvider) {
    return new AppLockViewModel_Factory(appLockRepositoryProvider);
  }

  public static AppLockViewModel newInstance(AppLockRepository appLockRepository) {
    return new AppLockViewModel(appLockRepository);
  }
}
