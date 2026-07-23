# Spy Guard ProGuard Security Rules

# Keep Hilt / Dagger generated classes
-keep class * extends javax.inject.Provider
-keep class dagger.hilt.** { *; }
-keep class com.spyguard.security.di.** { *; }

# Keep Room entities & DAOs
-keep class * extends androidx.room.RoomDatabase
-keep class com.spyguard.security.core.database.entity.** { *; }
-keep class com.spyguard.security.core.database.dao.** { *; }

# Keep DataStore models
-keep class com.spyguard.security.core.datastore.** { *; }

# Keep CameraX internals
-keep class androidx.camera.** { *; }

# Keep Gson models for local backup
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.spyguard.security.core.backup.** { *; }

# Obfuscate internal implementation logic
-repackageclasses 'com.spyguard.security.a'
-allowaccessmodification

# Remove log calls in release build
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
