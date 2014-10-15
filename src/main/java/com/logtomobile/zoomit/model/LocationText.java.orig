package com.logtomobile.zoomit.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class LocationText {
    private long mLocationTextId;

    private long mNewsId;
    @SerializedName("longitude")
    private double mLongtitude;
    @SerializedName("latitude")
    private double mLatitude;
    @SerializedName("radius")
    private int mRadius;
    @SerializedName("text")
    private String mText;
    @SerializedName("tag")
    private String mTag;

    public LocationText(long newsId, double latitude, double longtitude, int radius,
                        @NonNull String text, @NonNull String tag) {
        checkNotNull(text, "Text cannot be null");
        checkArgument(!text.isEmpty(), "Text cannot be empty");
        checkNotNull(tag, "Tag cannot be null");
        checkArgument(!tag.isEmpty(), "Tag cannot be empty");

        mNewsId = newsId;
        mLongtitude = longtitude;
        mLatitude = latitude;
        mRadius = radius;
        mText = text;
        mTag = tag;
    }

    public LocationText(long id, long newsId, double latitude, double longtitude, int radius,
                        @NonNull String text, @NonNull String tag) {
        checkNotNull(text, "Text cannot be null");
        checkArgument(!text.isEmpty(), "Text cannot be empty");
        checkNotNull(tag, "Tag cannot be null");
        checkArgument(!tag.isEmpty(), "Tag cannot be empty");

        mLocationTextId = id;
        mNewsId = newsId;
        mLongtitude = longtitude;
        mLatitude = latitude;
        mRadius = radius;
        mText = text;
        mTag = tag;
    }

    public LocationText() {
    }

    public long getLocationTextId() {
        return mLocationTextId;
    }

    public void setLocationTextId(long locationTextId) {
        mLocationTextId = locationTextId;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public long getNewsId() {
        return mNewsId;
    }

    public void setNewsId(long newsId) {
        mNewsId = newsId;
    }

    public double getLongtitude() {
        return mLongtitude;
    }

    public void setLongtitude(double longtitude) {
        mLongtitude = longtitude;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = mTag;
    }

    @Override
    public String toString() {
        return "LocationText{" +
                "mLocationTextId=" + mLocationTextId +
                ", mNewsId=" + mNewsId +
                ", mLongtitude=" + mLongtitude +
                ", mLatitude=" + mLatitude +
                ", mRadius=" + mRadius +
                ", mText='" + mText + '\'' +
                ", mTag='" + mTag + '\'' +
                '}';
    }
}
