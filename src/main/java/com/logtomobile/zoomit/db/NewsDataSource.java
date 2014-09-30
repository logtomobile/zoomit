package com.logtomobile.zoomit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.logtomobile.zoomit.db.table.NewsTable;
import com.logtomobile.zoomit.model.News;

/**
 * @author Bartosz Mądry
 */
public class NewsDataSource extends AbstractDataSource<News, NewsTable> {

    @NonNull
    @Override
    protected News cursorToEntity(@NonNull Cursor c) {
        int newsId = c.getInt(NewsTable.ColumnID.NEWS_ID);
        String title = c.getString(NewsTable.ColumnID.TITLE);
        String miniature = c.getString(NewsTable.ColumnID.MINIATURE_PATH);
        int currentLevel = c.getInt(NewsTable.ColumnID.CURRENT_LEVEL);
        int maxLevel = c.getInt(NewsTable.ColumnID.MAX_LEVEL);
        int order = c.getInt(NewsTable.ColumnID.ORDER);

        News n = new News(newsId, title, miniature, currentLevel, order);
        n.setMaxLevel(maxLevel);
        return n;
    }

    @NonNull
    @Override
    protected ContentValues entityToContentValues(@NonNull News news) {
        ContentValues values = new ContentValues();
        values.put(NewsTable.Column.NEWS_ID, news.getNewsId());
        values.put(NewsTable.Column.TITLE, news.getTitle());
        values.put(NewsTable.Column.MINIATURE_PATH, news.getMiniaturePath());
        values.put(NewsTable.Column.CURRENT_LEVEL, news.getCurrentLevel());
        values.put(NewsTable.Column.MAX_LEVEL, news.getMaxLevel());
        values.put(NewsTable.Column.ORDER, news.getOrder());

        return values;
    }
}
