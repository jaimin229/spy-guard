package com.spyguard.security.core.backup;

import android.content.Context;
import com.google.gson.Gson;
import com.spyguard.security.core.database.dao.IntruderLogDao;
import com.spyguard.security.core.database.dao.LockedAppDao;
import com.spyguard.security.core.security.KeystoreManager;
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
public final class LocalBackupManager_Factory implements Factory<LocalBackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<LockedAppDao> lockedAppDaoProvider;

  private final Provider<IntruderLogDao> intruderLogDaoProvider;

  private final Provider<KeystoreManager> keystoreManagerProvider;

  private final Provider<Gson> gsonProvider;

  public LocalBackupManager_Factory(Provider<Context> contextProvider,
      Provider<LockedAppDao> lockedAppDaoProvider, Provider<IntruderLogDao> intruderLogDaoProvider,
      Provider<KeystoreManager> keystoreManagerProvider, Provider<Gson> gsonProvider) {
    this.contextProvider = contextProvider;
    this.lockedAppDaoProvider = lockedAppDaoProvider;
    this.intruderLogDaoProvider = intruderLogDaoProvider;
    this.keystoreManagerProvider = keystoreManagerProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public LocalBackupManager get() {
    return newInstance(contextProvider.get(), lockedAppDaoProvider.get(), intruderLogDaoProvider.get(), keystoreManagerProvider.get(), gsonProvider.get());
  }

  public static LocalBackupManager_Factory create(Provider<Context> contextProvider,
      Provider<LockedAppDao> lockedAppDaoProvider, Provider<IntruderLogDao> intruderLogDaoProvider,
      Provider<KeystoreManager> keystoreManagerProvider, Provider<Gson> gsonProvider) {
    return new LocalBackupManager_Factory(contextProvider, lockedAppDaoProvider, intruderLogDaoProvider, keystoreManagerProvider, gsonProvider);
  }

  public static LocalBackupManager newInstance(Context context, LockedAppDao lockedAppDao,
      IntruderLogDao intruderLogDao, KeystoreManager keystoreManager, Gson gson) {
    return new LocalBackupManager(context, lockedAppDao, intruderLogDao, keystoreManager, gson);
  }
}
