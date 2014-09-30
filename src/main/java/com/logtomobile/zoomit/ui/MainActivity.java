package com.logtomobile.zoomit.ui;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.logtomobile.zoomit.R;
import com.logtomobile.zoomit.db.ZoomitDb;
import com.logtomobile.zoomit.model.News;
import com.logtomobile.zoomit.model.Question;
import com.logtomobile.zoomit.ui.dialog.LocationSettingsDialogFragment;
import com.logtomobile.zoomit.ui.event.LocationEvent;
import com.logtomobile.zoomit.ui.event.QuestionAnsweredEvent;
import com.logtomobile.zoomit.ui.fragment.ExplanationFragment;
import com.logtomobile.zoomit.ui.fragment.NewsListFragment;
import com.logtomobile.zoomit.ui.fragment.QuizFragment;
import com.logtomobile.zoomit.util.PositionTracker;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author Bartosz Mądry
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends ZoomitBaseActivity {
    public static interface onMenuItemClicked {
        public void onMinimizeAll();
    }
    @InjectView(R.id.flContent)
    private FrameLayout mFlContent;

    @Inject
    private ZoomitDb mDatabase;

    private MenuItem mMenuItemMinimizeAll;
    private onMenuItemClicked mMenuItemClickedListener;
    private FragmentManager mFragmentManager;
    private int mQuizPosition;
    private List<Question> mQuestionsForQuiz;
    private String mExplanationText;

    private PositionTracker mPositionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setLocationManager();

        setNewsFragment();
    }

    private void setLocationManager() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mEventBus.post(new LocationEvent(location));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        mPositionTracker = new PositionTracker(this, locationListener);

        if (mPositionTracker.canGetLocation()) {
            if (!mPositionTracker.isLocationAllowed()) {
                LocationSettingsDialogFragment fragment = new LocationSettingsDialogFragment();
                fragment.show(getSupportFragmentManager(), "location settings");
            }
        }
    }

    public Location getCurrentLocation() {
        Location location = null;
        if (mPositionTracker != null && mPositionTracker.canGetLocation() && mPositionTracker.isLocationAllowed()) {
            location = mPositionTracker.getCurrentLocation();
        }
        return location;
    }

    private void setNewsFragment() {
        if (mMenuItemMinimizeAll != null) {
            mMenuItemMinimizeAll.setVisible(true);
        }
        setFragment(new NewsListFragment(), NewsListFragment.TAG);
    }

    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(mFlContent.getId(), fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        mMenuItemMinimizeAll = menu.findItem(R.id.menu_minimize);
        View view = mMenuItemMinimizeAll.getActionView();
        TextView minimizeAll = (TextView) view.findViewById(R.id.txtvMinimizeAll);
        minimizeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemClickedListener != null) {
                    mMenuItemClickedListener.onMinimizeAll();
                }
            }
        });
        return true;
    }

    public void setOnMenuItemClickedListener(onMenuItemClicked listener) {
        mMenuItemClickedListener = listener;
    }

    public void startQuiz(News news) {
        mMenuItemMinimizeAll.setVisible(false);
        mExplanationText = "";
        mQuizPosition = 0;
        mQuestionsForQuiz = mDatabase.getQuestionsForNews(news);
        setFragment(QuizFragment.createInstance(mQuestionsForQuiz.get(mQuizPosition)), QuizFragment.TAG);
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            goBackToBaseFragment();
        }
    }

    public void goBackToBaseFragment() {
        mFragmentManager.popBackStack(NewsListFragment.TAG, 0);
        mMenuItemMinimizeAll.setVisible(true);
    }

    @Subscribe
    public void onQuestionAnswered(QuestionAnsweredEvent event) {
        if (!event.getScore()) {
            if (!mExplanationText.isEmpty()) {
                mExplanationText += "\n\n";
            }
            mExplanationText += mQuestionsForQuiz.get(mQuizPosition).getExplanation();
        }
        ++mQuizPosition;
        if (mQuizPosition != mQuestionsForQuiz.size()) {
            setFragment(QuizFragment.createInstance(mQuestionsForQuiz.get(mQuizPosition)), QuizFragment.TAG);
        } else {
            startAfterQuizAction();
        }
    }

    private void startAfterQuizAction() {
        if (!mExplanationText.isEmpty()) {
            setFragment(ExplanationFragment.createInstance(mExplanationText), ExplanationFragment.TAG);
        } else {
            goBackToBaseFragment();
        }

    }
}
