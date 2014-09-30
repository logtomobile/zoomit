package com.logtomobile.zoomit.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class News {
    private long mNewsId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("miniaturePath")
    private String mMiniaturePath;
    @SerializedName("newsText")
    private List<NewsText> mNewsTextList = new ArrayList<NewsText>();
    @SerializedName("questions")
    private List<Question> mQuestionList = new ArrayList<Question>();
    @SerializedName("locationText")
    private List<LocationText> mLocationTextList = new ArrayList<LocationText>();
    @SerializedName("order")
    private int mOrder;

    private int mCurrentLevel;
    private int mMaxLevel;

    public News(@NonNull String title, @NonNull String miniaturePath, int currentLevel, int order) {
        checkNotNull(title, "title cannot be null");
        checkNotNull(miniaturePath, "miniaturePath cannot be null");
        checkArgument(!title.isEmpty(), "title cannot be empty");
        checkArgument(!miniaturePath.isEmpty(), "miniaturePath cannot be empty");

        mTitle = title;
        mMiniaturePath = miniaturePath;
        mCurrentLevel = currentLevel;
        mOrder = order;
    }

    public News(long id, @NonNull String title, @NonNull String miniaturePath, int currentLevel, int order) {
        checkNotNull(title, "title cannot be null");
        checkNotNull(miniaturePath, "miniaturePath cannot be null");
        checkArgument(!title.isEmpty(), "title cannot be empty");
        checkArgument(!miniaturePath.isEmpty(), "miniaturePath cannot be empty");

        mNewsId = id;
        mTitle = title;
        mMiniaturePath = miniaturePath;
        mCurrentLevel = currentLevel;
        mOrder = order;
    }

    public News() {
    }


    public long getNewsId() {
        return mNewsId;
    }

    public void setNewsId(long id) {
        mNewsId = id;
    }

    public @NonNull String getTitle() {
        return mTitle;
    }

    public @NonNull String getMiniaturePath() {
        return mMiniaturePath;
    }

    public int getCurrentLevel() {
        return mCurrentLevel;
    }

    public void setCurrentLevel(int i) {
        mCurrentLevel = i;
    }

    public void setMaxLevel(int maxLevel) {
        mMaxLevel = maxLevel;
    }

    public int getMaxLevel() {
        return mMaxLevel;
    }

    public List<NewsText> getNewsTextList() {
        return mNewsTextList;
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }

    public List<LocationText> getLocationTextList() {
        return mLocationTextList;
    }

    public int getOrder() {
        return mOrder;
    }
}
