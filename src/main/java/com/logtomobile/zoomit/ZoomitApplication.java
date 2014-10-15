package com.logtomobile.zoomit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class ZoomitApplication extends Application {
    public static final String SETTINGS_NAME = "appSettings";
    public static final String SETTINGS_DEFAULT_LEVEL = "default_level";
    public static final String SETTINGS_APP_CREATED = "app_created";

    private static EventBus sEventBus;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sEventBus = new EventBus("zoomit eventbus");
        sContext = getApplicationContext();

    }

    public static @NonNull EventBus getEventBus() {
        return sEventBus;
    }

    public static @NonNull Context getAppContext() {
        return sContext;
    }

    public void saveToSettings(@NonNull String key, @NonNull Object o) {
        SharedPreferences settings = sContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = settings.edit();

        if (o instanceof String) {
            String value = (String) o;
            preferencesEditor.putString(key, value);
        } else if (o instanceof Integer) {
            int value = (Integer) o;
            preferencesEditor.putInt(key, value);
        } else if (o instanceof Boolean) {
            boolean value = (Boolean) o;
            preferencesEditor.putBoolean(SETTINGS_APP_CREATED, value);
        }
        preferencesEditor.apply();
    }

    public @NonNull Object loadFromSettings(@NonNull String key) {
        checkNotNull(key, "key cannot be null");
        Object o = null;
        SharedPreferences settings = sContext.getSharedPreferences(SETTINGS_NAME, Activity.MODE_PRIVATE);
        if (key.equals(SETTINGS_DEFAULT_LEVEL)) {
            o = settings.getInt(SETTINGS_DEFAULT_LEVEL, 0);
        } else if (key.equals(SETTINGS_APP_CREATED)) {
            o = settings.getBoolean(SETTINGS_APP_CREATED, false);
        }
        return o;
    }
}
