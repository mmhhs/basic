package com.feima.baseproject.util.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feima.baseproject.R;
import com.feima.baseproject.model.user.UserModelEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper
{
  private static final String DATABASE_NAME = "baseproject.db";
  private static final int DATABASE_VERSION =1;
  private static final String TAG = "DataBaseHelper";
  private static DataBaseHelper helper = null;
  private static final AtomicInteger usageCounter = new AtomicInteger(0);

  private Dao<UserModelEntity, String> userInfoDao;




  public DataBaseHelper(Context paramContext)
  {
    super(paramContext, DATABASE_NAME, null, DATABASE_VERSION, R.raw.data_config);
  }

  public static synchronized DataBaseHelper getHelper(Context paramContext)
  {
    if (helper == null)
      helper = new DataBaseHelper(paramContext);
    usageCounter.incrementAndGet();
    DataBaseHelper localDataBaseHelper = helper;
    return localDataBaseHelper;
  }

  public void close()
  {
    super.close();

  }


  public void onCreate(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource)
  {
    try
    {

      TableUtils.createTableIfNotExists(paramConnectionSource, UserModelEntity.class);

      return;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.getMessage();
    }
    return;
  }

  //数据库升级
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource, int paramInt1, int paramInt2)
  {
    try {
      //当数据库版本变化时，更新数据库
      TableUtils.dropTable(paramConnectionSource, UserModelEntity.class, true);


      onCreate(paramSQLiteDatabase, paramConnectionSource);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }
  //数据库降级
  public void onDowngrade (SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource, int oldVersion, int newVersion){
    if(newVersion<oldVersion){
      try {
        TableUtils.dropTable(paramConnectionSource, UserModelEntity.class, true);


        onCreate(paramSQLiteDatabase, paramConnectionSource);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }


  public Dao<UserModelEntity, String> getUserInfoDao()
          throws SQLException
  {
    if (this.userInfoDao == null)
      this.userInfoDao = getDao(UserModelEntity.class);
    return this.userInfoDao;
  }

}
