package com.logtomobile.zoomit.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.inject.Inject;

/**
 * @author Bartosz Mądry
 */
public class NewsTable extends AbstractTable {
    public static final class Column {
        public static final String NEWS_ID = "newsId";
        public static final String TITLE = "title";
        public static final String MINIATURE_PATH = "miniature_path";
        public static final String CURRENT_LEVEL = "current_level";
        public static final String MAX_LEVEL = "max_level";
        public static final String ORDER = "orderNews";
        public static final String EMPTY_EXPLANATION = "emptyExplanation";
    }

    public static final class ColumnID {
        public static final int NEWS_ID = 0;
        public static final int TITLE = 1;
        public static final int MINIATURE_PATH = 2;
        public static final int CURRENT_LEVEL = 3;
        public static final int MAX_LEVEL = 4;
        public static final int ORDER = 5;
        public static final int EMPTY_EXPLANATION = 6;
    }

    public static final String TABLE_NAME = "news_table";

    private static final String[] ALL_COLUMNS = {
            Column.NEWS_ID,
            Column.TITLE,
            Column.MINIATURE_PATH,
            Column.CURRENT_LEVEL,
            Column.MAX_LEVEL,
            Column.ORDER,
            Column.EMPTY_EXPLANATION
    };

    @Inject
    public NewsTable() {

    }

    @Override
    public @NonNull String getName() {
        return TABLE_NAME;
    }

    @Override
    public @NonNull String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public void create(@NonNull SQLiteDatabase db) {
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s integer primary key autoincrement, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER, " +
                        "%s INTEGER, " +
                        "%s INTEGER, " +
                        "%s TEXT);",

                TABLE_NAME,
                Column.NEWS_ID,
                Column.TITLE,
                Column.MINIATURE_PATH,
                Column.CURRENT_LEVEL,
                Column.MAX_LEVEL,
                Column.ORDER,
                Column.EMPTY_EXPLANATION
        );

        db.execSQL(sql);
    }
}
