package com.logtomobile.zoomit.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.inject.Inject;

/**
 * @author Bartosz Mądry
 */
public class NewsTextTable extends AbstractTable {
    public static final class Column {
        public static final String NEWS_ID = "newsId";
        public static final String LEVEL = "level";
        public static final String TEXT = "text";
    }

    public static final class ColumnID {
        public static final int NEWS_ID = 0;
        public static final int LEVEL = 1;
        public static final int TEXT = 2;
    }

    public static final String TABLE_NAME = "news_text_table";

    private static final String[] ALL_COLUMNS = {
            Column.NEWS_ID,
            Column.LEVEL,
            Column.TEXT
    };

    @Inject
    public NewsTextTable() {

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
                        "%s INTEGER , " +
                        "%s INTEGER, " +
                        "%s TEXT NOT NULL " +
                        ", PRIMARY KEY (%s, %s));",

                TABLE_NAME,
                Column.NEWS_ID,
                Column.LEVEL,
                Column.TEXT,
                Column.NEWS_ID,
                Column.LEVEL
        );

        db.execSQL(sql);
    }
}
