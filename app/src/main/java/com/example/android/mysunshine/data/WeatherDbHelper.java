package com.example.android.mysunshine.data;
/**
 * Created by Fzu_Xmu_bmw on 2015/4/20.
 * Email�� wmb007@126.com
 * MicroBlog:  http://weibo.com/wangmb007
 * Android�ṩ��һ����ΪSQLiteDatabase���࣬�����װ��һЩ�������ݿ��API��ʹ�ø������
 * ��ɶ����ݽ������(Create)����ѯ(Retrieve)������(Update)��ɾ��(Delete)��������Щ��
 * �����ΪCRUD������SQLiteDatabase��ѧϰ������Ӧ���ص�����execSQL()��rawQuery()������
 * execSQL()��������ִ��insert��delete��update��CREATETABLE֮���и�����Ϊ��SQL��䣻
 * rawQuery()��������ִ��select��䡣SQLiteDatabase��ר���ṩ�˶�Ӧ����ӡ�ɾ�������¡�
 * ��ѯ�Ĳ���������insert()��delete()��update()��query()����Щ����ʵ�����Ǹ���Щ��̫��
 * ��SQL�﷨�Ĳ���ʹ�õģ�������ϤSQL�﷨�ĳ���Ա���ԣ�ֱ��ʹ��execSQL()��rawQuery()����
 * ִ��SQL������������ݵ���ӡ�ɾ�������¡���ѯ������
 *
 * [SQLiteOpenHelper]
 * ���Ӧ��ʹ�õ���SQLite���ݿ⣬���û�����ʹ�����ʱ����Ҫ����Ӧ��ʹ�õ������ݿ��ṹ�����
 * һЩ��ʼ����¼�����������������ʱ��Ҳ��Ҫ�����ݱ�ṹ���и��¡��� Androidϵͳ��Ϊ������
 * ����һ����ΪSQLiteOpenHelper���࣬�������ڶ����ݿ�汾���й���������һ�������࣬�����
 * ��������ʹ�á�ʹ��������ʵ������
 * onCreate(SQLiteDatabase)��onUpgrade(SQLiteDatabase,int,int)����
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mysunshine.data.WeatherContract.*;

/**
 * Manages a local database for weather data.
 * SQLiteOpenHelper���࣬�������ڶ����ݿ�汾���й���������һ�������࣬����̳�������ʹ��
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    // �����������ݿ�ģʽ,������������ݿ�汾��
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {// onCreate�������ݿ��һ�α�������ʱ��ִ�У����紴����,��ʼ�����ݵȡ�
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude ��γ��
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

    @Override   //onUpgrade�������ݿ���Ҫ�����µ�ʱ��ִ�У�����ɾ���ɱ������±�
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /**   ������ݿ�ֻ���������ݵĻ���,�����������������Ǽ򵥵ض������ݲ����¿�ʼ
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