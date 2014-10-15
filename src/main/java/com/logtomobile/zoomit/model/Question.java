package com.logtomobile.zoomit.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class Question implements Serializable{
    private long mQuestionId;
    private long mNewsId;
    @SerializedName("text")
    private String mText;
    @SerializedName("explanation")
    private String mExplanation;
    @SerializedName("answers")
    private List<Answer> mAnswers;
    @SerializedName("order")
    private int mOrder;

    public Question(long newsId, String text, String explanation, int order) {
        checkNotNull(text, "Text cannot be null");
        checkArgument(!text.isEmpty(), "Text cannot be empty");
        checkNotNull(explanation, "Explanation cannot be null");
        checkArgument(!explanation.isEmpty(), "Explanation cannot be empty");

        mNewsId = newsId;
        mText = text;
        mExplanation = explanation;
        mOrder = order;
    }

    public Question(long questionId, long newsId, String text, String explanation, int order) {
        checkNotNull(text, "Text cannot be null");
        checkArgument(!text.isEmpty(), "Text cannot be empty");
        checkNotNull(explanation, "Explanation cannot be null");
        checkArgument(!explanation.isEmpty(), "Explanation cannot be empty");

        mQuestionId = questionId;
        mNewsId = newsId;
        mText = text;
        mExplanation = explanation;
        mOrder = order;
    }

    public Question() {

    }

    public long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(long questionId) {
        mQuestionId = questionId;
    }

    public long getNewsId() {
        return mNewsId;
    }

    public @NonNull String getText() {
        return mText;
    }

    public void setAnswers(List<Answer> answers) {
        mAnswers = answers;
    }

    public List<Answer> getAnswers() {
        return mAnswers;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public void setNewsId(long id) {
        mNewsId = id;
    }

    public int getOrder() {
        return mOrder;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mText='" + mText + '\'' +
                ", mExplanation='" + mExplanation + '\'' +
                '}';
    }
}
