package com.spyguard.security.core.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.spyguard.security.core.database.dao.AppLockLogDao;
import com.spyguard.security.core.database.dao.AppLockLogDao_Impl;
import com.spyguard.security.core.database.dao.IntruderLogDao;
import com.spyguard.security.core.database.dao.IntruderLogDao_Impl;
import com.spyguard.security.core.database.dao.LockedAppDao;
import com.spyguard.security.core.database.dao.LockedAppDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SpyGuardDatabase_Impl extends SpyGuardDatabase {
  private volatile LockedAppDao _lockedAppDao;

  private volatile IntruderLogDao _intruderLogDao;

  private volatile AppLockLogDao _appLockLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `locked_apps` (`packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `isSystemApp` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `lockedAt` INTEGER NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `intruder_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `photoPath` TEXT NOT NULL, `failedAttempts` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `app_lock_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `isSuccess` INTEGER NOT NULL, `attemptCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3c8eb7d7ab74aac2d55caaef8dffc9a6')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `locked_apps`");
        db.execSQL("DROP TABLE IF EXISTS `intruder_logs`");
        db.execSQL("DROP TABLE IF EXISTS `app_lock_logs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLockedApps = new HashMap<String, TableInfo.Column>(5);
        _columnsLockedApps.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLockedApps.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLockedApps.put("isSystemApp", new TableInfo.Column("isSystemApp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLockedApps.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLockedApps.put("lockedAt", new TableInfo.Column("lockedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLockedApps = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLockedApps = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLockedApps = new TableInfo("locked_apps", _columnsLockedApps, _foreignKeysLockedApps, _indicesLockedApps);
        final TableInfo _existingLockedApps = TableInfo.read(db, "locked_apps");
        if (!_infoLockedApps.equals(_existingLockedApps)) {
          return new RoomOpenHelper.ValidationResult(false, "locked_apps(com.spyguard.security.core.database.entity.LockedAppEntity).\n"
                  + " Expected:\n" + _infoLockedApps + "\n"
                  + " Found:\n" + _existingLockedApps);
        }
        final HashMap<String, TableInfo.Column> _columnsIntruderLogs = new HashMap<String, TableInfo.Column>(6);
        _columnsIntruderLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("photoPath", new TableInfo.Column("photoPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("failedAttempts", new TableInfo.Column("failedAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIntruderLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIntruderLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIntruderLogs = new TableInfo("intruder_logs", _columnsIntruderLogs, _foreignKeysIntruderLogs, _indicesIntruderLogs);
        final TableInfo _existingIntruderLogs = TableInfo.read(db, "intruder_logs");
        if (!_infoIntruderLogs.equals(_existingIntruderLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "intruder_logs(com.spyguard.security.core.database.entity.IntruderLogEntity).\n"
                  + " Expected:\n" + _infoIntruderLogs + "\n"
                  + " Found:\n" + _existingIntruderLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsAppLockLogs = new HashMap<String, TableInfo.Column>(6);
        _columnsAppLockLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppLockLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppLockLogs.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppLockLogs.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppLockLogs.put("isSuccess", new TableInfo.Column("isSuccess", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppLockLogs.put("attemptCount", new TableInfo.Column("attemptCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppLockLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppLockLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppLockLogs = new TableInfo("app_lock_logs", _columnsAppLockLogs, _foreignKeysAppLockLogs, _indicesAppLockLogs);
        final TableInfo _existingAppLockLogs = TableInfo.read(db, "app_lock_logs");
        if (!_infoAppLockLogs.equals(_existingAppLockLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "app_lock_logs(com.spyguard.security.core.database.entity.AppLockLogEntity).\n"
                  + " Expected:\n" + _infoAppLockLogs + "\n"
                  + " Found:\n" + _existingAppLockLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3c8eb7d7ab74aac2d55caaef8dffc9a6", "90cfa1c89e0afffbcf83b2c1750fa045");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "locked_apps","intruder_logs","app_lock_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `locked_apps`");
      _db.execSQL("DELETE FROM `intruder_logs`");
      _db.execSQL("DELETE FROM `app_lock_logs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(LockedAppDao.class, LockedAppDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(IntruderLogDao.class, IntruderLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppLockLogDao.class, AppLockLogDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public LockedAppDao lockedAppDao() {
    if (_lockedAppDao != null) {
      return _lockedAppDao;
    } else {
      synchronized(this) {
        if(_lockedAppDao == null) {
          _lockedAppDao = new LockedAppDao_Impl(this);
        }
        return _lockedAppDao;
      }
    }
  }

  @Override
  public IntruderLogDao intruderLogDao() {
    if (_intruderLogDao != null) {
      return _intruderLogDao;
    } else {
      synchronized(this) {
        if(_intruderLogDao == null) {
          _intruderLogDao = new IntruderLogDao_Impl(this);
        }
        return _intruderLogDao;
      }
    }
  }

  @Override
  public AppLockLogDao appLockLogDao() {
    if (_appLockLogDao != null) {
      return _appLockLogDao;
    } else {
      synchronized(this) {
        if(_appLockLogDao == null) {
          _appLockLogDao = new AppLockLogDao_Impl(this);
        }
        return _appLockLogDao;
      }
    }
  }
}
