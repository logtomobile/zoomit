package com.logtomobile.zoomit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.logtomobile.zoomit.db.table.LocationTextTable;
import com.logtomobile.zoomit.model.LocationText;

/**
 * @author Bartosz Mądry
 */
public class LocationTextDataSource extends AbstractDataSource<LocationText, LocationTextTable> {

    @NonNull
    @Override
    protected LocationText cursorToEntity(@NonNull Cursor c) {
        int locationTextId = c.getInt(LocationTextTable.ColumnID.LOCATION_TEXT_ID);
        int newsId= c.getInt(LocationTextTable.ColumnID.NEWS_ID);
        double lat = c.getDouble(LocationTextTable.ColumnID.LATITUDE);
        double lon = c.getDouble(LocationTextTable.ColumnID.LONGTITUDE);
        int radius = c.getInt(LocationTextTable.ColumnID.RADIUS);
        String text = c.getString(LocationTextTable.ColumnID.TEXT);
        String tag = c.getString(LocationTextTable.ColumnID.TAG);

        return new LocationText(locationTextId, newsId, lat, lon, radius, text, tag);
    }

    @NonNull
    @Override
    protected ContentValues entityToContentValues(@NonNull LocationText locationText) {
        ContentValues values = new ContentValues();
        values.put(LocationTextTable.Column.LOCATION_TEXT_ID, locationText.getLocationTextId());
        values.put(LocationTextTable.Column.NEWS_ID, locationText.getNewsId());
        values.put(LocationTextTable.Column.LATITUDE, locationText.getLatitude());
        values.put(LocationTextTable.Column.LONGTITUDE, locationText.getLongtitude());
        values.put(LocationTextTable.Column.RADIUS, locationText.getRadius());
        values.put(LocationTextTable.Column.TEXT, locationText.getText());
        values.put(LocationTextTable.Column.TAG, locationText.getTag());

        return values;
    }
}
