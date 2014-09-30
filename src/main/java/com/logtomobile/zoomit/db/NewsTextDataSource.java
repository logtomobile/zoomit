package com.logtomobile.zoomit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.logtomobile.zoomit.db.table.NewsTextTable;
import com.logtomobile.zoomit.model.News;
import com.logtomobile.zoomit.model.NewsText;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class NewsTextDataSource extends AbstractDataSource<NewsText, NewsTextTable> {
    @NonNull
    @Override
    protected NewsText cursorToEntity(@NonNull Cursor c) {
        int newsId = c.getInt(NewsTextTable.ColumnID.NEWS_ID);
        int level = c.getInt(NewsTextTable.ColumnID.LEVEL);
        String text = c.getString(NewsTextTable.ColumnID.TEXT);

        return new NewsText(newsId, level, text);
    }

    @NonNull
    @Override
    protected ContentValues entityToContentValues(@NonNull NewsText newsText) {
        ContentValues values = new ContentValues();
        values.put(NewsTextTable.Column.NEWS_ID, newsText.getNewsId());
        values.put(NewsTextTable.Column.LEVEL, newsText.getLevel());
        values.put(NewsTextTable.Column.TEXT, newsText.getText());

        return values;
    }

    @NonNull
    public NewsText getNewsText(@NonNull News news) {
        checkNotNull(news, "News cannot be null");

        return _get(String.format("%s = %d and %s = %d",
                NewsTextTable.Column.NEWS_ID,
                news.getNewsId(),
                NewsTextTable.Column.LEVEL,
                news.getCurrentLevel()
        )).get(0);
    }
}
