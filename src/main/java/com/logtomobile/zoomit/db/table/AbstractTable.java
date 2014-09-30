package com.logtomobile.zoomit.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * @author Bartosz Mądry
 */
public abstract class AbstractTable {
    public abstract @NonNull String getName();
    public abstract @NonNull String[] getAllColumns();
    public abstract void create(@NonNull SQLiteDatabase db);
}
