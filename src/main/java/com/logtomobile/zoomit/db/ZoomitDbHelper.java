package com.logtomobile.zoomit.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.google.inject.Inject;
import com.logtomobile.zoomit.ZoomitApplication;
import com.logtomobile.zoomit.db.table.AnswerTable;
import com.logtomobile.zoomit.db.table.LocationTextTable;
import com.logtomobile.zoomit.db.table.NewsTable;
import com.logtomobile.zoomit.db.table.NewsTextTable;
import com.logtomobile.zoomit.db.table.QuestionTable;

/**
 * @author Bartosz Mądry
 */
public class ZoomitDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ZoomitDB";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDatabase;

    @Inject
    public ZoomitDbHelper() {
        super(ZoomitApplication.getAppContext(), DB_NAME, null, DB_VERSION);
        mDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        NewsTable newsTable = new NewsTable();
        newsTable.create(db);

        NewsTextTable newsTextTable = new NewsTextTable();
        newsTextTable.create(db);

        LocationTextTable locationTextTable = new LocationTextTable();
        locationTextTable.create(db);

        QuestionTable questionTable = new QuestionTable();
        questionTable.create(db);

        AnswerTable answerTable = new AnswerTable();
        answerTable.create(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public @NonNull SQLiteDatabase getDatabase() {
        return mDatabase;
    }
}
