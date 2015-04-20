package com.example.android.mysunshine.data;
/**
 * Created by Fzu_Xmu_bmw on 2015/4/20.
 * Email： wmb007@126.com
 * MicroBlog:  http://weibo.com/wangmb007
 * Android提供了一个名为SQLiteDatabase的类，该类封装了一些操作数据库的API，使用该类可以
 * 完成对数据进行添加(Create)、查询(Retrieve)、更新(Update)和删除(Delete)操作（这些操
 * 作简称为CRUD）。对SQLiteDatabase的学习，我们应该重点掌握execSQL()和rawQuery()方法。
 * execSQL()方法可以执行insert、delete、update和CREATETABLE之类有更改行为的SQL语句；
 * rawQuery()方法可以执行select语句。SQLiteDatabase还专门提供了对应于添加、删除、更新、
 * 查询的操作方法：insert()、delete()、update()和query()。这些方法实际上是给那些不太了
 * 解SQL语法的菜鸟使用的，对于熟悉SQL语法的程序员而言，直接使用execSQL()和rawQuery()方法
 * 执行SQL语句就能完成数据的添加、删除、更新、查询操作。
 *
 * [SQLiteOpenHelper]
 * 如果应用使用到了SQLite数据库，在用户初次使用软件时，需要创建应用使用到的数据库表结构及添加
 * 一些初始化记录，另外在软件升级的时候，也需要对数据表结构进行更新。在 Android系统，为我们提
 * 供了一个名为SQLiteOpenHelper的类，该类用于对数据库版本进行管理，该类是一个抽象类，必须继
 * 承它才能使用。使用它必须实现它的
 * onCreate(SQLiteDatabase)，onUpgrade(SQLiteDatabase,int,int)方法
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mysunshine.data.WeatherContract.*;

/**
 * Manages a local database for weather data.
 * SQLiteOpenHelper的类，该类用于对数据库版本进行管理，该类是一个抽象类，必须继承它才能使用
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    // 如果你更改数据库模式,你必须增加数据库版本。
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {// onCreate：当数据库第一次被建立的时候被执行，例如创建表,初始化数据等。
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude 经纬度
        // TBD
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY," +
                LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
                LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL, " +
                "UNIQUE (" + LocationEntry.COLUMN_LOCATION_SETTING +") ON CONFLICT IGNORE"+
                " );";
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                WeatherEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                WeatherEntry.COLUMN_DATETEXT + " TEXT NOT NULL, " +
                WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL," +

                WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
                WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +

                WeatherContract.WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + WeatherEntry.COLUMN_DATETEXT + ", " +
                WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override   //onUpgrade：当数据库需要被更新的时候执行，例如删除旧表，创建新表
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /**   这个数据库只是在线数据的缓存,所以它的升级政策是简单地丢弃数据并重新开始
         This database is only a cache for online data, so its upgrade policy is to simply
         to discard the data and start over Note that this only fires if you change the
         version number for your database. It does NOT depend on the version number for
         your application. If you want to update the schema without wiping data,
         commenting out the next 2 lines should be your top priority before modifying
         this method.
         */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}