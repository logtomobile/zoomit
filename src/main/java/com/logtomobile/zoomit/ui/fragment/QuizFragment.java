package com.logtomobile.zoomit.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.logtomobile.zoomit.R;
import com.logtomobile.zoomit.model.Answer;
import com.logtomobile.zoomit.model.Question;
import com.logtomobile.zoomit.ui.event.QuestionAnsweredEvent;
import com.logtomobile.zoomit.ui.view.AutoResizeTextView;
import com.logtomobile.zoomit.util.BundleConstants;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class QuizFragment extends ZoomitBaseFragment {
    public static final String EXTRA_QUESTION = "extra_question";
    public static final String TAG = "quiz_fragment_tag";

    private AutoResizeTextView mTxtvQuestion;

    private TextView  mTxtvLastAnswer;
    private TextView mTxtvSecondLastAnswer;
    private TextView mTxtvThirdLastAnswer;
    private TextView mTxtvFourthLastAnswer;

    private TextView mTxtvLastLetter;
    private TextView mTxtvSecondLastLetter;
    private TextView mTxtvThirdLastLetter;
    private TextView mTxtvFourthLastLetter;

    private RelativeLayout mRlLastAnswer;
    private RelativeLayout mRlSecondLastAnswer;
    private RelativeLayout mRlThirdLastAnswer;
    private RelativeLayout mRlFourthLastAnswer;

    private View viewSeparatorThreeAnswers;
    private View viewSeparatorFourAnswers;

    private View mHighlightLast;
    private View mHighlightSecondLast;
    private View mHighlightThirdLast;
    private View mHighlightFourthLast;

    private Question mQuestion;
    private List<Answer> mAnswerList;
    private int mAnswered;
    private RelativeLayout mAnsweredLayout;

    public static @NonNull
    QuizFragment createInstance(@NonNull Question question) {
        checkNotNull(question, "Question cannot be null");

        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_QUESTION, question);

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(extras);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey(EXTRA_QUESTION)) {
                mQuestion = (Question) extras.getSerializable(EXTRA_QUESTION);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        mTxtvQuestion = (AutoResizeTextView) mRootView.findViewById(R.id.txtvQuestion);

        mTxtvLastAnswer = (TextView) mRootView.findViewById(R.id.txtvAnswerLast);
        mTxtvSecondLastAnswer = (TextView) mRootView.findViewById(R.id.txtvAnswerSecondLast);
        mTxtvThirdLastAnswer = (TextView) mRootView.findViewById(R.id.txtvAnswerThirdLast);
        mTxtvFourthLastAnswer = (TextView) mRootView.findViewById(R.id.txtvAnswerFourthLast);

        mTxtvLastLetter = (TextView) mRootView.findViewById(R.id.txtvLetterLast);
        mTxtvSecondLastLetter = (TextView) mRootView.findViewById(R.id.txtvLetterSecondLast);
        mTxtvThirdLastLetter = (TextView) mRootView.findViewById(R.id.txtvLetterThirdLast);
        mTxtvFourthLastLetter = (TextView) mRootView.findViewById(R.id.txtvLetterFourthLast);

        mRlLastAnswer = (RelativeLayout) mRootView.findViewById(R.id.rlLastAnswer);
        mRlSecondLastAnswer = (RelativeLayout) mRootView.findViewById(R.id.rlSecondLastAnswer);
        mRlThirdLastAnswer = (RelativeLayout) mRootView.findViewById(R.id.rlThirdLastAnswer);
        mRlFourthLastAnswer = (RelativeLayout) mRootView.findViewById(R.id.rlFourthLastAnswer);

        viewSeparatorThreeAnswers = mRootView.findViewById(R.id.separatorThreeAnswers);
        viewSeparatorFourAnswers = mRootView.findViewById(R.id.separatorFourAnswers);

        mHighlightLast = mRootView.findViewById(R.id.highlightLast);
        mHighlightSecondLast = mRootView.findViewById(R.id.highlightSecondLast);
        mHighlightThirdLast = mRootView.findViewById(R.id.highlightThirdLast);
        mHighlightFourthLast = mRootView.findViewById(R.id.highlightFourthLast);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setVisibility();
        setListeners();
    }

    private void setListeners() {
        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(mRlLastAnswer)) {
                    mAnsweredLayout = mRlLastAnswer;
                    if (mAnswerList.size() == 2) {
                        mAnswered = 1;
                    } else if (mAnswerList.size() == 3) {
                        mAnswered = 2;
                    } else {
                        mAnswered = 3;
                    }
                } else if(v.equals(mRlSecondLastAnswer)) {
                    mAnsweredLayout = mRlSecondLastAnswer;
                    if (mAnswerList.size() == 2) {
                        mAnswered = 0;
                    } else if (mAnswerList.size() == 3){
                        mAnswered = 1;
                    } else {
                        mAnswered = 2;
                    }

                } else if(v.equals(mRlThirdLastAnswer)) {
                    mAnsweredLayout = mRlThirdLastAnswer;
                    if (mAnswerList.size() == 3) {
                        mAnswered = 0;
                    } else {
                        mAnswered = 1;
                    }

                } else if(v.equals(mRlFourthLastAnswer)) {
                    mAnsweredLayout = mRlFourthLastAnswer;
                    mAnswered = 0;
                }
                final boolean score = mAnswerList.get(mAnswered).getValue() > 0;

                setBackgroundColors();
                setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        afterAnswered(score);
                    }
                }, BundleConstants.TIME_FOR_SLEEP_ANSWER);
            }
        };
        mRlLastAnswer.setOnClickListener(answerListener);
        mRlSecondLastAnswer.setOnClickListener(answerListener);
        mRlThirdLastAnswer.setOnClickListener(answerListener);
        mRlFourthLastAnswer.setOnClickListener(answerListener);
    }

    private void setClickable(boolean clickable) {
        mRlLastAnswer.setClickable(clickable);
        mRlSecondLastAnswer.setClickable(clickable);
        mRlThirdLastAnswer.setClickable(clickable);
        mRlFourthLastAnswer.setClickable(clickable);
    }

    private void setBackgroundColors() {
        if (mAnsweredLayout.equals(mRlLastAnswer)) {
            mHighlightLast.setVisibility(View.VISIBLE);
            mTxtvLastAnswer.setTextColor(getResources().getColor(R.color.quiz_background));
            mTxtvLastLetter.setTextColor(getResources().getColor(R.color.quiz_background));
        } else if (mAnsweredLayout.equals(mRlSecondLastAnswer)) {
            mHighlightSecondLast.setVisibility(View.VISIBLE);
            mTxtvSecondLastAnswer.setTextColor(getResources().getColor(R.color.quiz_background));
            mTxtvSecondLastLetter.setTextColor(getResources().getColor(R.color.quiz_background));
        } else if (mAnsweredLayout.equals(mRlThirdLastAnswer)) {
            mHighlightThirdLast.setVisibility(View.VISIBLE);
            mTxtvThirdLastAnswer.setTextColor(getResources().getColor(R.color.quiz_background));
            mTxtvThirdLastLetter.setTextColor(getResources().getColor(R.color.quiz_background));
        } else if (mAnsweredLayout.equals(mRlThirdLastAnswer)) {
            mHighlightFourthLast.setVisibility(View.VISIBLE);
            mTxtvFourthLastAnswer.setTextColor(getResources().getColor(R.color.quiz_background));
            mTxtvFourthLastLetter.setTextColor(getResources().getColor(R.color.quiz_background));
        }
    }

    private void afterAnswered(boolean score) {
        mEventBus.post(new QuestionAnsweredEvent(score));
    }

    private void setVisibility() {
        mTxtvQuestion.setText(mQuestion.getText());
        mAnswerList = mQuestion.getAnswers();

        if (mAnswerList.size() < 4) {
            mRlFourthLastAnswer.setVisibility(View.GONE);
            viewSeparatorFourAnswers.setVisibility(View.GONE);
        }
        if (mAnswerList.size() < 3) {
            mRlThirdLastAnswer.setVisibility(View.GONE);
            viewSeparatorThreeAnswers.setVisibility(View.GONE);
        }

        if (mAnswerList.size() == 4) {
            setValues(4);
        } else if (mAnswerList.size() == 3) {
            setValues(3);
        } else {
            setValues(2);
        }
    }

    private void setValues(int i) {
        if (i == 4) {
            mTxtvFourthLastLetter.setText("A");
            mTxtvThirdLastLetter.setText("B");
            mTxtvSecondLastLetter.setText("C");
            mTxtvLastLetter.setText("D");
            mTxtvFourthLastAnswer.setText(mAnswerList.get(0).getText());
            mTxtvThirdLastAnswer.setText(mAnswerList.get(1).getText());
            mTxtvSecondLastAnswer.setText(mAnswerList.get(2).getText());
            mTxtvLastAnswer.setText(mAnswerList.get(3).getText());
        } else if (i == 3) {
            mTxtvThirdLastLetter.setText("A");
            mTxtvSecondLastLetter.setText("B");
            mTxtvLastLetter.setText("C");
            mTxtvThirdLastAnswer.setText(mAnswerList.get(0).getText());
            mTxtvSecondLastAnswer.setText(mAnswerList.get(1).getText());
            mTxtvLastAnswer.setText(mAnswerList.get(2).getText());
        } else {
            mTxtvSecondLastLetter.setText("A");
            mTxtvLastLetter.setText("B");
            mTxtvSecondLastAnswer.setText(mAnswerList.get(0).getText());
            mTxtvLastAnswer.setText(mAnswerList.get(1).getText());
        }
    }
}
