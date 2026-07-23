package com.spyguard.security.feature_settings;

import com.spyguard.security.core.backup.LocalBackupManager;
import com.spyguard.security.core.datastore.PreferencesManager;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<LocalBackupManager> localBackupManagerProvider;

  public SettingsViewModel_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<LocalBackupManager> localBackupManagerProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.localBackupManagerProvider = localBackupManagerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(preferencesManagerProvider.get(), localBackupManagerProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider,
      Provider<LocalBackupManager> localBackupManagerProvider) {
    return new SettingsViewModel_Factory(preferencesManagerProvider, localBackupManagerProvider);
  }

  public static SettingsViewModel newInstance(PreferencesManager preferencesManager,
      LocalBackupManager localBackupManager) {
    return new SettingsViewModel(preferencesManager, localBackupManager);
  }
}
