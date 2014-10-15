package com.logtomobile.zoomit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.logtomobile.zoomit.db.table.AnswerTable;
import com.logtomobile.zoomit.db.table.QuestionTable;
import com.logtomobile.zoomit.model.Answer;
import com.logtomobile.zoomit.model.Question;

/**
 * @author Bartosz Mądry
 */
public class AnswerDataSource extends AbstractDataSource<Answer, AnswerTable> {

    @NonNull
    @Override
    protected Answer cursorToEntity(@NonNull Cursor c) {
        int answerId = c.getInt(AnswerTable.ColumnID.ANSWER_ID);
        int questionId = c.getInt(AnswerTable.ColumnID.QUESTION_ID);
        String text = c.getString(AnswerTable.ColumnID.TEXT);
        int value = c.getInt(AnswerTable.ColumnID.VALUE);
        int order = c.getInt(AnswerTable.ColumnID.ORDER);

        return new Answer(answerId ,questionId, text, value, order);
    }

    @NonNull
    @Override
    protected ContentValues entityToContentValues(@NonNull Answer answer) {
        ContentValues values = new ContentValues();
        values.put(AnswerTable.Column.ANSWER_ID, answer.getAnswerId());
        values.put(AnswerTable.Column.QUESTION_ID, answer.getQuestionId());
        values.put(AnswerTable.Column.TEXT, answer.getText());
        values.put(AnswerTable.Column.VALUE, answer.getValue());
        values.put(AnswerTable.Column.ORDER, answer.getOrder());

        return values;
    }
}
