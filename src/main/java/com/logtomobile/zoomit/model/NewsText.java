package com.logtomobile.zoomit.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class NewsText {
    private long mNewsId;
    @SerializedName("level")
    private int mLevel;
    @SerializedName("text")
    private String mText;

    public NewsText(long newsId, int level, @NonNull String text) {
        checkNotNull(text, "text cannot be null");
        checkArgument(!text.isEmpty(), "text cannot be empty");

        mNewsId = newsId;
        mLevel = level;
        mText = text;
    }

    public NewsText() {

    }

    public long getNewsId() {
        return mNewsId;
    }

    public int getLevel() {
        return mLevel;
    }

    public @NonNull String getText() {
        return mText;
    }

    public void setNewsId(long id) {
        mNewsId = id;
    }

    @Override
    public String toString() {
        return "NewsText{" +
                "mLevel=" + mLevel +
                ", mText='" + mText + '\'' +
                '}';
    }
}
