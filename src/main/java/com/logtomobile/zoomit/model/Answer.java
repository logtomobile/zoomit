package com.logtomobile.zoomit.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class Answer implements Serializable {
    private long mAnswerId;
    private long mQuestionId;
    @SerializedName("text")
    private String mText;
    @SerializedName("value")
    private int mValue;
    @SerializedName("order")
    private int mOrder;

    public Answer(long answerId, long questionId, String text, int value, int order) {
        checkNotNull(text, "text cannot be null");
        checkArgument(!text.isEmpty(), "text cannot be empty");

        mAnswerId = answerId;
        mQuestionId = questionId;
        mText = text;
        mValue = value;
        mOrder = order;
    }

    public Answer(long questionId, String text, int value, int order) {
        checkNotNull(text, "text cannot be null");
        checkArgument(!text.isEmpty(), "text cannot be empty");

        mQuestionId = questionId;
        mText = text;
        mValue = value;
        mOrder = order;
    }

    public Answer() {

    }

    public void setAnswerId(long id) {
        mAnswerId = id;
    }

    public long getAnswerId() {
        return mAnswerId;
    }

    public long getQuestionId() {
        return mQuestionId;
    }

    public @NonNull String getText() {
        return mText;
    }

    public int getValue() {
        return mValue;
    }

    public void setQuestionId(long id) {
        mQuestionId = id;
    }

    public void setOrder(int order) {
        mOrder = order;
    }

    public int getOrder() {
        return mOrder;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "mText='" + mText + '\'' +
                ", mValue=" + mValue +
                '}';
    }
}
