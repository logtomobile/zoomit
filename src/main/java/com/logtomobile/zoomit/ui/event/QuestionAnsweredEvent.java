package com.logtomobile.zoomit.ui.event;

/**
 * @author Bartosz Mądry
 */
public class QuestionAnsweredEvent {
    private final boolean mScore;
    public QuestionAnsweredEvent(boolean score) {
        mScore = score;
    }

    public boolean getScore() {
        return mScore;
    }
}
