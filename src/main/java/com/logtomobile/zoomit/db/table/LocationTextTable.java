package com.logtomobile.zoomit.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.inject.Inject;

/**
 * @author Bartosz Mądry
 */
public class LocationTextTable extends AbstractTable {
    public static final class Column {
        public static final String LOCATION_TEXT_ID = "locationTextId";
        public static final String NEWS_ID = "newsId";
        public static final String LATITUDE = "latitude";
        public static final String LONGTITUDE = "longtitude";
        public static final String RADIUS = "radius";
        public static final String TEXT = "text";
        public static final String TAG = "tag";
    }

    public static final class ColumnID {
        public static final int LOCATION_TEXT_ID = 0;
        public static final int NEWS_ID = 1;
        public static final int LATITUDE = 2;
        public static final int LONGTITUDE = 3;
        public static final int RADIUS = 4;
        public static final int TEXT = 5;
        public static final int TAG = 6;
    }

    public static final String TABLE_NAME = "location_text_table";

    private static final String[] ALL_COLUMNS = {
            Column.LOCATION_TEXT_ID,
            Column.NEWS_ID,
            Column.LATITUDE,
            Column.LONGTITUDE,
            Column.RADIUS,
            Column.TEXT,
            Column.TAG
    };

    @Inject
    public LocationTextTable() {

    }
    @NonNull
    @Override
    public String getName() {
        return TABLE_NAME;
    }

    @NonNull
    @Override
    public String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public void create(@NonNull SQLiteDatabase db) {
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s integer primary key autoincrement, " +
                        "%s INTEGER, " +
                        "%s REAL, " +
                        "%s REAL, " +
                        "%s INTEGER, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL);",

                TABLE_NAME,
                Column.LOCATION_TEXT_ID,
                Column.NEWS_ID,
                Column.LATITUDE,
                Column.LONGTITUDE,
                Column.RADIUS,
                Column.TEXT,
                Column.TAG
        );

        db.execSQL(sql);
    }
}
