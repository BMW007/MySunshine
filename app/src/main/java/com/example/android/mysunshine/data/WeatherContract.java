/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.mysunshine.data;

import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 * Contract:SQL数据库的主要原则之一是构架：一个关于这个数据库是如何组织的一个正式声明。
 * 构架构架反映在创建SQL数据库的语句中。你可能会发现创建一个同伴类（companion class）
 * 很有用，同伴类同时被称作合约类（contract class），其中明确规定了你的构架的布局，以
 * 一种系统且自说明的方式。一个合约类（contract class）是一个常量的容器，这些常量定义
 * 了URI，表的名字，列的名字。合约类允许你在同一个包的其他类中使用这些名字常量。这就允许
 * 了你在一个地方改变列名，而同时把它传播到代码的其他地方去。
 **/
public class WeatherContract {

    /* Inner class that defines the table contents of the 【location table】 */
    public static final class LocationEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "location";

        // The location setting string is what will be sent to openweathermap
        // as the location query.
        public static final String COLUMN_LOCATION_SETTING = "location_setting";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_CITY_NAME = "city_name";

        // In order to uniquely pinpoint the location on the map when we launch the
        // map intent, we store the latitude and longitude as returned by openweathermap.
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";
    }

    /* Inner class that defines the table contents of the 【weather table】 */
    public static final class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";

        // Column with the foreign key into the location table.
        public static final String COLUMN_LOC_KEY = "location_id";
        // Date, stored as Text with format yyyy-MM-dd
        public static final String COLUMN_DATETEXT = "date";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_WEATHER_ID = "weather_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_SHORT_DESC = "short_desc";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        // Humidity is stored as a float representing percentage湿度是表示百分比存储 一个浮点数
        public static final String COLUMN_HUMIDITY = "humidity";

        // pressure is stored as a float representing percentage
        public static final String COLUMN_PRESSURE = "pressure";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String COLUMN_WIND_SPEED = "wind";

        // Degrees are meteorological degrees气象度表示 (e.g, 0 is north, 180 is south).  Stored as floats.
        public static final String COLUMN_DEGREES = "degrees";
    }
}
