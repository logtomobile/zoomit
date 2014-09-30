package com.logtomobile.zoomit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.inject.Inject;
import com.logtomobile.zoomit.db.ZoomitDbHelper;
import com.logtomobile.zoomit.db.table.AbstractTable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz Mądry
 *
 */
/*package*/ abstract class AbstractDataSource<Entity, Table extends AbstractTable> {
    @Inject
    protected ZoomitDbHelper mDbHelper;

    @Inject
    protected Table mEntitiesTable;

    protected int mInsertConflictAlgorithm = SQLiteDatabase.CONFLICT_REPLACE;

    protected abstract @NonNull Entity cursorToEntity(@NonNull Cursor c);
    protected abstract @NonNull ContentValues entityToContentValues(@NonNull Entity entity);

    protected @NonNull
    List<Entity> _get(@NonNull String where) {
        checkNotNull(where, "where cannot be null");
        checkArgument(!where.isEmpty(), "where cannot be empty");

        SQLiteDatabase db = mDbHelper.getDatabase();

        Cursor c = db.query(mEntitiesTable.getName(), mEntitiesTable.getAllColumns(), where,
                null, null, null, null);
        List<Entity> entities = new ArrayList<Entity>();

        if (c != null) {
            if (c.moveToFirst() && c.getCount() > 0) {
                do {
                    entities.add(cursorToEntity(c));
                } while (c.moveToNext());
            }

            c.close();
        }
        return entities;
    }

    protected @NonNull
    List<Entity> _getOrdered(@Nullable String where, @NonNull String order) {
        checkNotNull(order, "order cannot be null");
        checkArgument(!order.isEmpty(), "order cannot be empty");

        SQLiteDatabase db = mDbHelper.getDatabase();

        Cursor c = db.query(mEntitiesTable.getName(), mEntitiesTable.getAllColumns(), where,
                null, null, null, order);
        List<Entity> entities = new ArrayList<Entity>();

        if (c != null) {
            if (c.moveToFirst() && c.getCount() > 0) {
                do {
                    entities.add(cursorToEntity(c));
                } while (c.moveToNext());
            }

            c.close();
        }
        return entities;
    }

    protected void _update(@NonNull String where, Entity newEntity) {
        checkNotNull(where, "where cannot be null");
        checkArgument(!where.isEmpty(), "where cannot be empty");

        SQLiteDatabase db = mDbHelper.getDatabase();
        db.update(mEntitiesTable.getName(), entityToContentValues(newEntity), where, null);
    }

    public long insert(@NonNull Entity entity) {
        SQLiteDatabase db = mDbHelper.getDatabase();
        long id = db.insertWithOnConflict(mEntitiesTable.getName(), null, entityToContentValues(entity),
                mInsertConflictAlgorithm);

        return id;
    }

    public void insert(@NonNull Iterable<Entity> entities) {
        SQLiteDatabase db = mDbHelper.getDatabase();
        db.beginTransaction();

        for (Entity e : entities) {
            insert(e);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public @NonNull List<Entity> getAll() {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        SQLiteDatabase db = mDbHelper.getDatabase();
        Cursor c = db.query(mEntitiesTable.getName(), mEntitiesTable.getAllColumns(),
                null, null, null, null, null);

        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    entities.add(cursorToEntity(c));
                } while (c.moveToNext());
            }

            c.close();
        }

        return entities;
    }

    protected void delete(@NonNull String where) {
        checkNotNull(where, "where cannot be null");
        checkArgument(!where.isEmpty(), "where cannot be empty");

        SQLiteDatabase db = mDbHelper.getDatabase();
        db.delete(mEntitiesTable.getName(), where, null);
    }

    public void clear() {
        SQLiteDatabase db = mDbHelper.getDatabase();
        db.delete(mEntitiesTable.getName(), null, null);
    }
}
