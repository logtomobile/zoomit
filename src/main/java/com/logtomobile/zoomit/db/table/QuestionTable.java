package com.logtomobile.zoomit.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.inject.Inject;

/**
 * @author Bartosz Mądry
 */
public class QuestionTable extends AbstractTable {
    public static final class Column {
        public static final String QUESTION_ID = "questionId";
        public static final String NEWS_ID = "newsId";
        public static final String TEXT = "text";
        public static final String EXPLANATION = "explanation";
        public static final String ORDER = "questionOrder";
    }

    public static final class ColumnID {
        public static final int QUESTION_ID = 0;
        public static final int NEWS_ID = 1;
        public static final int TEXT = 2;
        public static final int EXPLANATION = 3;
        public static final int ORDER = 4;
    }
    public static final String TABLE_NAME = "question_table";

    private static final String[] ALL_COLUMNS = {
            Column.QUESTION_ID,
            Column.NEWS_ID,
            Column.TEXT,
            Column.EXPLANATION,
            Column.ORDER
    };

    @Inject
    public QuestionTable() {

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
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER);",
                TABLE_NAME,
                Column.QUESTION_ID,
                Column.NEWS_ID,
                Column.TEXT,
                Column.EXPLANATION,
                Column.ORDER
        );

        db.execSQL(sql);
    }
}
