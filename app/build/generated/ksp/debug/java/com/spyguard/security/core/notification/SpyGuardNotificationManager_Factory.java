package com.spyguard.security.core.notification;

import android.content.Context;
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
public final class SpyGuardNotificationManager_Factory implements Factory<SpyGuardNotificationManager> {
  private final Provider<Context> contextProvider;

  public SpyGuardNotificationManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SpyGuardNotificationManager get() {
    return newInstance(contextProvider.get());
  }

  public static SpyGuardNotificationManager_Factory create(Provider<Context> contextProvider) {
    return new SpyGuardNotificationManager_Factory(contextProvider);
  }

  public static SpyGuardNotificationManager newInstance(Context context) {
    return new SpyGuardNotificationManager(context);
  }
}
