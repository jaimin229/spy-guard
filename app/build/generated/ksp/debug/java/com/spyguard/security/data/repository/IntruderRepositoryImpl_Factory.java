package com.spyguard.security.data.repository;

import com.spyguard.security.core.database.dao.IntruderLogDao;
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
public final class IntruderRepositoryImpl_Factory implements Factory<IntruderRepositoryImpl> {
  private final Provider<IntruderLogDao> intruderLogDaoProvider;

  public IntruderRepositoryImpl_Factory(Provider<IntruderLogDao> intruderLogDaoProvider) {
    this.intruderLogDaoProvider = intruderLogDaoProvider;
  }

  @Override
  public IntruderRepositoryImpl get() {
    return newInstance(intruderLogDaoProvider.get());
  }

  public static IntruderRepositoryImpl_Factory create(
      Provider<IntruderLogDao> intruderLogDaoProvider) {
    return new IntruderRepositoryImpl_Factory(intruderLogDaoProvider);
  }

  public static IntruderRepositoryImpl newInstance(IntruderLogDao intruderLogDao) {
    return new IntruderRepositoryImpl(intruderLogDao);
  }
}
