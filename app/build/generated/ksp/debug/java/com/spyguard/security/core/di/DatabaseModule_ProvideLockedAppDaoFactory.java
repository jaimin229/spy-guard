package com.spyguard.security.core.di;

import com.spyguard.security.core.database.SpyGuardDatabase;
import com.spyguard.security.core.database.dao.LockedAppDao;
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
public final class DatabaseModule_ProvideLockedAppDaoFactory implements Factory<LockedAppDao> {
  private final Provider<SpyGuardDatabase> dbProvider;

  public DatabaseModule_ProvideLockedAppDaoFactory(Provider<SpyGuardDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LockedAppDao get() {
    return provideLockedAppDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideLockedAppDaoFactory create(
      Provider<SpyGuardDatabase> dbProvider) {
    return new DatabaseModule_ProvideLockedAppDaoFactory(dbProvider);
  }

  public static LockedAppDao provideLockedAppDao(SpyGuardDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLockedAppDao(db));
  }
}
