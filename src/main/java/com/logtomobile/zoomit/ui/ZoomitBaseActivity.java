package com.logtomobile.zoomit.ui;

import android.os.Bundle;

import com.google.common.eventbus.EventBus;
import com.logtomobile.zoomit.ZoomitApplication;

import roboguice.activity.RoboFragmentActivity;

/**
 * @author Bartosz Mądry
 */
public class ZoomitBaseActivity extends RoboFragmentActivity {
    protected EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = ZoomitApplication.getEventBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
    }
}
