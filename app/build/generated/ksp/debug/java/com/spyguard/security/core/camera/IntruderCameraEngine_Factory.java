package com.spyguard.security.core.camera;

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
public final class IntruderCameraEngine_Factory implements Factory<IntruderCameraEngine> {
  private final Provider<Context> contextProvider;

  public IntruderCameraEngine_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public IntruderCameraEngine get() {
    return newInstance(contextProvider.get());
  }

  public static IntruderCameraEngine_Factory create(Provider<Context> contextProvider) {
    return new IntruderCameraEngine_Factory(contextProvider);
  }

  public static IntruderCameraEngine newInstance(Context context) {
    return new IntruderCameraEngine(context);
  }
}
