package com.logtomobile.zoomit.ui.event;

import android.location.Location;

/**
 * @author Bartosz Mądry
 */
public class LocationEvent {
    Location mLocation;

    public LocationEvent(Location location) {
        mLocation = location;
    }

    public Location getLocation() {
        return mLocation;
    }
}
