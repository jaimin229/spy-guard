package com.spyguard.security.feature_intruder;

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
public final class IntruderViewModel_Factory implements Factory<IntruderViewModel> {
  private final Provider<IntruderRepository> intruderRepositoryProvider;

  public IntruderViewModel_Factory(Provider<IntruderRepository> intruderRepositoryProvider) {
    this.intruderRepositoryProvider = intruderRepositoryProvider;
  }

  @Override
  public IntruderViewModel get() {
    return newInstance(intruderRepositoryProvider.get());
  }

  public static IntruderViewModel_Factory create(
      Provider<IntruderRepository> intruderRepositoryProvider) {
    return new IntruderViewModel_Factory(intruderRepositoryProvider);
  }

  public static IntruderViewModel newInstance(IntruderRepository intruderRepository) {
    return new IntruderViewModel(intruderRepository);
  }
}
