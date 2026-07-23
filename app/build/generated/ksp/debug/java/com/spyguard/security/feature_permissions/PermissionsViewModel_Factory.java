package com.spyguard.security.feature_permissions;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class PermissionsViewModel_Factory implements Factory<PermissionsViewModel> {
  @Override
  public PermissionsViewModel get() {
    return newInstance();
  }

  public static PermissionsViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PermissionsViewModel newInstance() {
    return new PermissionsViewModel();
  }

  private static final class InstanceHolder {
    private static final PermissionsViewModel_Factory INSTANCE = new PermissionsViewModel_Factory();
  }
}
