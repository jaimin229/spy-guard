package com.spyguard.security.core.di;

import com.spyguard.security.core.database.SpyGuardDatabase;
import com.spyguard.security.core.database.dao.IntruderLogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideIntruderLogDaoFactory implements Factory<IntruderLogDao> {
  private final Provider<SpyGuardDatabase> dbProvider;

  public DatabaseModule_ProvideIntruderLogDaoFactory(Provider<SpyGuardDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public IntruderLogDao get() {
    return provideIntruderLogDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideIntruderLogDaoFactory create(
      Provider<SpyGuardDatabase> dbProvider) {
    return new DatabaseModule_ProvideIntruderLogDaoFactory(dbProvider);
  }

  public static IntruderLogDao provideIntruderLogDao(SpyGuardDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideIntruderLogDao(db));
  }
}
