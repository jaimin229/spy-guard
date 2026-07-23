package com.spyguard.security.core.datastore;

import android.content.Context;
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
public final class PreferencesManager_Factory implements Factory<PreferencesManager> {
  private final Provider<Context> contextProvider;

  private final Provider<KeystoreManager> keystoreManagerProvider;

  public PreferencesManager_Factory(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider) {
    this.contextProvider = contextProvider;
    this.keystoreManagerProvider = keystoreManagerProvider;
  }

  @Override
  public PreferencesManager get() {
    return newInstance(contextProvider.get(), keystoreManagerProvider.get());
  }

  public static PreferencesManager_Factory create(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider) {
    return new PreferencesManager_Factory(contextProvider, keystoreManagerProvider);
  }

  public static PreferencesManager newInstance(Context context, KeystoreManager keystoreManager) {
    return new PreferencesManager(context, keystoreManager);
  }
}
