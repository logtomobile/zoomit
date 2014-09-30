package com.logtomobile.zoomit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.logtomobile.zoomit.db.table.NewsTable;
import com.logtomobile.zoomit.db.table.QuestionTable;
import com.logtomobile.zoomit.model.News;
import com.logtomobile.zoomit.model.Question;

/**
 * @author Bartosz Mądry
 */
public class QuestionDataSource extends AbstractDataSource<Question, QuestionTable> {

    @NonNull
    @Override
    protected Question cursorToEntity(@NonNull Cursor c) {
        int questionId = c.getInt(QuestionTable.ColumnID.QUESTION_ID);
        int newsId = c.getInt(QuestionTable.ColumnID.NEWS_ID);
        String text = c.getString(QuestionTable.ColumnID.TEXT);
        String explanation = c.getString(QuestionTable.ColumnID.EXPLANATION);
        int order = c.getInt(QuestionTable.ColumnID.ORDER);

        return new Question(questionId, newsId, text, explanation, order);
    }

    @NonNull
    @Override
    protected ContentValues entityToContentValues(@NonNull Question question) {
        ContentValues values = new ContentValues();
        values.put(QuestionTable.Column.QUESTION_ID, question.getQuestionId());
        values.put(QuestionTable.Column.NEWS_ID, question.getNewsId());
        values.put(QuestionTable.Column.TEXT, question.getText());
        values.put(QuestionTable.Column.EXPLANATION, question.getExplanation());
        values.put(QuestionTable.Column.ORDER, question.getOrder());

        return values;
    }
}
