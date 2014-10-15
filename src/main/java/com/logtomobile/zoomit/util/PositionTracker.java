package com.logtomobile.zoomit.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class PositionTracker extends Service {
    private static final String TAG = "POSITION_TRACKER";
    private static final int MIN_DISTANCE_FOR_UPDATE = 10; // [meters]
    private static final int MIN_TIME_BW_UPDATES = 1000 * 60; // [miliseconds]

    private final Context mContext;

    private boolean mLocationAllowed = false;
    private boolean mHaveSourceForLocation = false;

    private Location mLocation;
    private double mLatitude;
    private double mLongtitude;

    private LocationListener mLocationListener;

    private LocationManager mLocationManager;

    public PositionTracker(@NonNull Context context, @NonNull LocationListener listener) {
        mContext = checkNotNull(context, "Context cannot be null");
        mLocationListener = checkNotNull(listener, "Location listener cannot be null");
        getCurrentLocation();
    }

    public Location getCurrentLocation() {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            boolean mIsGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean mIsNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            mLocationAllowed = mIsGpsEnabled || mIsNetworkEnabled;
            mHaveSourceForLocation = mIsGpsEnabled || isOnline();

            if (mHaveSourceForLocation) {
                if (mIsNetworkEnabled && isOnline()) {
                    Log.d(TAG, "Getting location from network");
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_FOR_UPDATE, mLocationListener);

                    if (mLocationManager != null) {
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        updateCoordinates();
                    }
                }

                if (mIsGpsEnabled) {
                    if (mLocation == null) {
                        Log.d(TAG, "Getting location from gps");
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_FOR_UPDATE, mLocationListener);
                        if (mLocationManager != null) {
                            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            updateCoordinates();
                        }
                    }
            } else {
                Log.d(TAG, "No provider available");
            }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while Connecting LocationManager", e);
        }

        return mLocation;
    }

    private void updateCoordinates() {
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
            mLongtitude = mLocation.getLongitude();
        }
    }

    public void stopUsingTracker() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    public boolean isLocationAllowed() {
        return mLocationAllowed;
    }

    public boolean canGetLocation() {
        return mHaveSourceForLocation;
    }

    public double getLatitude() {
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
        }

        return mLatitude;
    }

    public double getLongtitude() {
        if (mLocation != null) {
            mLongtitude = mLocation.getLongitude();
        }
        return mLongtitude;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private boolean isOnline() {
        ConnectivityManager mConnectivityManger = (ConnectivityManager) mContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManger.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }
}
