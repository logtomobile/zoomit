package com.logtomobile.zoomit.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.logtomobile.zoomit.R;
import com.logtomobile.zoomit.ui.MainActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class ExplanationFragment extends ZoomitBaseFragment {
    public static final String EXTRA_TEXT = "explanation_text";
    public static final String TAG = "explanation_tag";
    private String mExplanationText;

    private TextView mTxtvExplanationText;
    private TextView mTxtvExplanationSkip;
    private MainActivity mMainActivity;

    public static @NonNull
    ExplanationFragment createInstance(@NonNull String explanation) {
        checkNotNull(explanation, "Explanation text cannot be null");

        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_TEXT, explanation);

        ExplanationFragment fragment = new ExplanationFragment();
        fragment.setArguments(extras);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey(EXTRA_TEXT)) {
                mExplanationText = extras.getString(EXTRA_TEXT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_explanation, container, false);
        mTxtvExplanationSkip = (TextView) mRootView.findViewById(R.id.txtvExplanationSkip);
        mTxtvExplanationText = (TextView) mRootView.findViewById(R.id.txtvExplanationText);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTxtvExplanationText.setText(mExplanationText);
        mTxtvExplanationSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goBackToBaseFragment();
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivity = null;
    }
}
