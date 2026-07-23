package com.spyguard.security.feature_dashboard;

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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<AppLockRepository> appLockRepositoryProvider;

  private final Provider<IntruderRepository> intruderRepositoryProvider;

  public DashboardViewModel_Factory(Provider<AppLockRepository> appLockRepositoryProvider,
      Provider<IntruderRepository> intruderRepositoryProvider) {
    this.appLockRepositoryProvider = appLockRepositoryProvider;
    this.intruderRepositoryProvider = intruderRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(appLockRepositoryProvider.get(), intruderRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<AppLockRepository> appLockRepositoryProvider,
      Provider<IntruderRepository> intruderRepositoryProvider) {
    return new DashboardViewModel_Factory(appLockRepositoryProvider, intruderRepositoryProvider);
  }

  public static DashboardViewModel newInstance(AppLockRepository appLockRepository,
      IntruderRepository intruderRepository) {
    return new DashboardViewModel(appLockRepository, intruderRepository);
  }
}
