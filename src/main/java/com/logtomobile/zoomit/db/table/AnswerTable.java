package com.logtomobile.zoomit.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.inject.Inject;

/**
 * @author Bartosz Mądry
 */
public class AnswerTable extends AbstractTable {
    public static final class Column {
        public static final String ANSWER_ID = "answerId";
        public static final String QUESTION_ID = "questionId";
        public static final String TEXT = "text";
        public static final String VALUE = "value";
        public static final String ORDER = "orderAnswer";
    }

    public static final class ColumnID {
        public static final int ANSWER_ID = 0;
        public static final int QUESTION_ID = 1;
        public static final int TEXT = 2;
        public static final int VALUE = 3;
        public static final int ORDER = 4;
    }
    public static final String TABLE_NAME = "answer_table";

    private static final String[] ALL_COLUMNS = {
            Column.ANSWER_ID,
            Column.QUESTION_ID,
            Column.TEXT,
            Column.VALUE,
            Column.ORDER
    };

    @Inject
    public AnswerTable() {

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
                        "%s INTEGER , " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER, " +
                        "%s INTEGER);",
                TABLE_NAME,
                Column.ANSWER_ID,
                Column.QUESTION_ID,
                Column.TEXT,
                Column.VALUE,
                Column.ORDER
        );

        db.execSQL(sql);
    }
}
