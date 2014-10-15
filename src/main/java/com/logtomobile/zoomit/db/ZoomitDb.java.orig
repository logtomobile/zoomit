package com.logtomobile.zoomit.db;

import android.support.annotation.NonNull;

import com.google.inject.Inject;
import com.logtomobile.zoomit.db.table.AnswerTable;
import com.logtomobile.zoomit.db.table.LocationTextTable;
import com.logtomobile.zoomit.db.table.NewsTable;
import com.logtomobile.zoomit.db.table.NewsTextTable;
import com.logtomobile.zoomit.db.table.QuestionTable;
import com.logtomobile.zoomit.model.Answer;
import com.logtomobile.zoomit.model.LocationText;
import com.logtomobile.zoomit.model.News;
import com.logtomobile.zoomit.model.NewsText;
import com.logtomobile.zoomit.model.Question;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class ZoomitDb {
    @Inject
    private NewsDataSource mNewsDataSource;

    @Inject
    private NewsTextDataSource mNewsTextDataSource;

    @Inject
    private QuestionDataSource mQuestionDataSource;

    @Inject
    private AnswerDataSource mAnswerDataSource;

    @Inject
    private LocationTextDataSource mLocationTextDataSource;

    @Inject
    public ZoomitDb() {
    }

    public long addNews(@NonNull News news) {
        checkNotNull(news, "news cannot be null");
        return mNewsDataSource.insert(news);
    }

    public long addQuestion(@NonNull Question question) {
        checkNotNull(question, "question cannot be null");
        return mQuestionDataSource.insert(question);
    }

    public long addAnswer(@NonNull Answer answer) {
        checkNotNull(answer, "Answer cannot be null");
        return mAnswerDataSource.insert(answer);
    }

    public void updateNews(@NonNull News news) {
        checkNotNull(news, "news cannot be null");
        mNewsDataSource._update(String.format("%s = %d",
                NewsTextTable.Column.NEWS_ID,
                news.getNewsId()),news);
    }

    public void addNewsText(@NonNull NewsText newsText) {
        checkNotNull(newsText, "news text cannot be null");
        mNewsTextDataSource.insert(newsText);
    }

    public void addLocationText(@NonNull LocationText locationText) {
        checkNotNull(locationText, "location text cannot be null");
        mLocationTextDataSource.insert(locationText);
    }

    public NewsText getNewsText(@NonNull News news) {
        checkNotNull(news, "news cannot be null");
        return mNewsTextDataSource.getNewsText(news);
    }

    public List<LocationText> getLocationTextForNews(@NonNull News news) {
        checkNotNull(news, "News cannot be null");
        return mLocationTextDataSource._get(String.format("%s = %d",
                LocationTextTable.Column.NEWS_ID,
                news.getNewsId()));
    }

    public List<News> getAllNews() {
        return mNewsDataSource._getOrdered(null, String.format("%s ASC",
                NewsTable.Column.ORDER));
    }

    public void setMaxLevelForNews(News news) {
        int count = mNewsTextDataSource._get(String.format("%s = %d",
                NewsTextTable.Column.NEWS_ID,
                news.getNewsId())).size();
        news.setMaxLevel(count - 1);
        updateNews(news);
    }

    public void minimizeAllText() {
        List<News> allNews = getAllNews();
        for (News n : allNews) {
            System.out.println("News current "+n.getCurrentLevel());
            n.setCurrentLevel(0);
            updateNews(n);
        }
        allNews = getAllNews();
        for (News n : allNews) {
            System.out.println("News current second "+n.getCurrentLevel());
        }
    }

    public List<Question> getQuestionsForNews(News n) {
        List<Question> mQuestions = mQuestionDataSource._getOrdered(String.format("%s = %d",
                QuestionTable.Column.NEWS_ID,
                n.getNewsId()),
                String.format("%s ASC",
                QuestionTable.Column.ORDER));

        for (Question q : mQuestions) {
            List<Answer> mAnswers = mAnswerDataSource._getOrdered(String.format("%s = %d",
                    AnswerTable.Column.QUESTION_ID,
                    q.getQuestionId()), String.format("%s ASC",
                    AnswerTable.Column.ORDER));
            q.setAnswers(mAnswers);
        }

        return mQuestions;
    }
}
