package com.logtomobile.zoomit.ui.fragment;

import android.os.Bundle;

import com.google.common.eventbus.EventBus;
import com.logtomobile.zoomit.ZoomitApplication;

import roboguice.fragment.RoboFragment;

/**
 * @author Bartosz Mądry
 */
public class ZoomitBaseFragment extends RoboFragment {
    protected EventBus mEventBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = ZoomitApplication.getEventBus();
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }
}
