package com.logtomobile.zoomit.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.logtomobile.zoomit.R;
import com.logtomobile.zoomit.ZoomitApplication;
import com.logtomobile.zoomit.db.ZoomitDb;
import com.logtomobile.zoomit.model.Answer;
import com.logtomobile.zoomit.model.LocationText;
import com.logtomobile.zoomit.model.News;
import com.logtomobile.zoomit.model.NewsText;
import com.logtomobile.zoomit.model.Question;
import com.logtomobile.zoomit.ui.MainActivity;
import com.logtomobile.zoomit.ui.event.LocationEvent;
import com.logtomobile.zoomit.util.BundleConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bartosz Mądry
 */
public class NewsListFragment extends ZoomitBaseFragment {
    public class NewsAdapter extends ArrayAdapter<News> {
        class ViewHolder {
            private ImageView mImgvMiniature;
            private TextView mTxtvTitle;
            private TextView mTxtvNewsText;
            private TextView mTxtvNewsPercent;
            private TextView mTxtvNewsTimeAppro;
            private ImageView mImgvPlus;
            private ImageView mImgvMinus;
            private TextView mTxtvDontUnderstand;
        }

        private final Context mContext;
        private final List<News> mNews;

        public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> news) {
            super(context, resource, news);

            checkNotNull(context, "context cannot be null");
            checkNotNull(news, "news acannot be null");

            mContext = context;
            mNews = news;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder views;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.lvlitem_news, parent, false);
                views = new ViewHolder();
                views.mImgvMiniature = (ImageView) convertView.findViewById(R.id.imgvNewsMiniature);
                views.mTxtvNewsText = (TextView) convertView.findViewById(R.id.txtvNewsText);
                views.mTxtvTitle = (TextView) convertView.findViewById(R.id.txtvNewsTitle);
                views.mTxtvNewsPercent = (TextView) convertView.findViewById(R.id.txtvNewsPercentText);
                views.mTxtvNewsTimeAppro = (TextView) convertView.findViewById(R.id.txtvNewsAproximateTime);
                views.mTxtvDontUnderstand = (TextView) convertView.findViewById(R.id.txtvDontUnderstand);
                views.mImgvMinus = (ImageView) convertView.findViewById(R.id.imgvMinus);
                views.mImgvPlus = (ImageView) convertView.findViewById(R.id.imgvPlus);

                convertView.setTag(views);
            } else {
                views = (ViewHolder) convertView.getTag();
            }

            final News n = mNews.get(position);

            views.mTxtvTitle.setText(n.getTitle());
            String newsText = mDatabase.getNewsText(n).getText();
            List<LocationText> locationTextForNews = mDatabase.getLocationTextForNews(n);
            for (LocationText locationText : locationTextForNews) {
                if (mCurrentLocation != null && mCurrentLocation.getLatitude() != 0) {
                    Location secondLocation = new Location("l");
                    secondLocation.setLatitude(locationText.getLatitude());
                    secondLocation.setLongitude(locationText.getLongtitude());
                    float distance = mCurrentLocation.distanceTo(secondLocation);
                    distance /= 1000;

                    System.out.println("Distance "+distance +" radius "+locationText.getRadius()+" text "+locationText.getText());
                    if (distance < locationText.getRadius()) {
                        newsText = newsText.replaceAll(locationText.getTag(), locationText.getText());
                    } else {
                        newsText = newsText.replaceAll(locationText.getTag()+" ", "");
                    }
                } else {
                    newsText = newsText.replaceAll(locationText.getTag()+" ", "");
                }
            }
            views.mTxtvNewsText.setText(newsText);
            String[] split = newsText.split(" ");
            String[] splitTitle = n.getTitle().split(" ");

            views.mTxtvNewsTimeAppro.setText("approx: "+countTime(split.length + splitTitle.length + 2)+" sec");

            views.mImgvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (n.getMaxLevel() != n.getCurrentLevel()) {
                        n.setCurrentLevel(n.getCurrentLevel() + 1);
                        mDatabase.updateNews(n);
                        notifyDataSetChanged();
                    }
                }
            });

            views.mImgvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (n.getCurrentLevel() != 0) {
                        n.setCurrentLevel(n.getCurrentLevel() - 1);
                        mDatabase.updateNews(n);
                        notifyDataSetChanged();
                    }
                }
            });

            views.mTxtvNewsPercent.setText(countPercentage(n)+"%");
            views.mImgvMiniature.setImageBitmap(getImage(n.getMiniaturePath()));

            if (mDatabase.getQuestionsForNews(n).isEmpty()) {
                views.mTxtvDontUnderstand.setVisibility(View.INVISIBLE);
            } else {
                views.mTxtvDontUnderstand.setVisibility(View.VISIBLE);
            }
            views.mTxtvDontUnderstand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMainActivity != null) {
                        mMainActivity.startQuiz(n);
                    }
                }
            });

            return convertView;
        }

        private Bitmap getImage(String photoPath) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//                File sdcard = Environment.getExternalStorageDirectory();
//                File filesDir = new File(sdcard, "Zoomit");
//                File finalFile = new File(filesDir, photoPath);
//
//                return BitmapFactory.decodeFile(finalFile.getPath(), options);
//            } else {
//                return null;
//            }
            AssetManager assetManager = mContext.getAssets();
            InputStream is;
            Bitmap bitmap = null;

            try {
                is = assetManager.open(photoPath);
                bitmap = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        private long countTime(int words) {
            double wordPerSec = BundleConstants.SPEED_READING_PER_MINUTE / 60;
            double score = words / wordPerSec;


            return Math.round(score);
        }

        private long countPercentage(News n) {
            double score = (n.getCurrentLevel() + 1) * 100 / (n.getMaxLevel() + 1);
            return Math.round(score);
        }
    }
    public static final String TAG = "news_list_fragment_tag";
    @InjectView(R.id.lvNews)
    private ListView mLvNews;

    @Inject
    private ZoomitApplication mApplication;

    @Inject
    private ZoomitDb mDatabase;

    private List<News> mNews = new ArrayList<News>();
    private MainActivity mMainActivity;

    private NewsAdapter mAdapterNews;
    private Location mCurrentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newslist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNews();
        setListView();
        setListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGson(getStringJson());
    }

    private String getStringJson() {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String result = "";
//            File sdcard = Environment.getExternalStorageDirectory();
//            File filesDir = new File(sdcard, "Zoomit");
//            File finalFile = new File(filesDir, "json.json");
            try {
                String s;
//                FileReader fr = new FileReader(finalFile);
                InputStream is = mMainActivity.getAssets().open("json.json");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                while ((s = br.readLine()) != null ) {
                    result += s;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
//        }
//        return null;
    }

    private void getGson(String s) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<News>>(){}.getType();
        List<News> allNews = gson.fromJson(s, collectionType);
        if (allNews != null) {
            for (News n : allNews) {
                int newsId = (n.getTitle() + n.getMiniaturePath()).hashCode();
                n.setNewsId(newsId);
                mDatabase.addNews(n);
                for (NewsText newsText : n.getNewsTextList()) {
                    newsText.setNewsId(newsId);
                    mDatabase.addNewsText(newsText);
                }
                for (LocationText locationText: n.getLocationTextList()) {
                    locationText.setNewsId(newsId);
                    long locationId = (locationText.getNewsId() + locationText.getLatitude() + locationText.getLongtitude() +
                                       locationText.getRadius() + locationText.getText() + locationText.getTag()).hashCode();
                    locationText.setLocationTextId(locationId);
                    System.out.println("LocationText "+locationText.toString());
                    mDatabase.addLocationText(locationText);
                }
                if (n.getQuestionList() != null) {
                    for (Question question : n.getQuestionList()) {
                        question.setNewsId(newsId);
                        long questionID = (question.getText() + question.getExplanation() + question.getNewsId()).hashCode();
                        question.setQuestionId(questionID);
                        mDatabase.addQuestion(question);
                        for (Answer a : question.getAnswers()) {
                            a.setQuestionId(questionID);
                            long answerId = (a.getText() + a.getValue() + a.getQuestionId()).hashCode();
                            a.setAnswerId(answerId);
                            mDatabase.addAnswer(a);
                        }
                    }
                }

                mDatabase.setMaxLevelForNews(n);
            }
        }
    }

    private void setListener() {
        MainActivity.onMenuItemClicked mItemClickedListener = new MainActivity.onMenuItemClicked() {
            @Override
            public void onMinimizeAll() {
                mDatabase.minimizeAllText();
                for (News n : mNews) {
                    n.setCurrentLevel(0);
                }
                mAdapterNews.notifyDataSetChanged();
            }
        };
        if (mMainActivity != null) {
            mMainActivity.setOnMenuItemClickedListener(mItemClickedListener);
        }
    }

    private void getNews() {
        Boolean appCreated = (Boolean) mApplication.loadFromSettings(ZoomitApplication.SETTINGS_APP_CREATED);
        if (!appCreated) {
            mApplication.saveToSettings(ZoomitApplication.SETTINGS_APP_CREATED, true);
        }
        mNews = mDatabase.getAllNews();
    }


    private void setListView() {
        mCurrentLocation = mMainActivity.getCurrentLocation();

        if (mCurrentLocation != null) {
            System.out.println("Basic location "+mCurrentLocation.getLatitude() +" "+mCurrentLocation.getLongitude());
        } else {
            System.out.println("Basic location == null");
        }

        mAdapterNews = new NewsAdapter(getActivity(), R.layout.lvlitem_news, mNews);
        mLvNews.setAdapter(mAdapterNews);
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

    @Subscribe
    public void onLocationChanged(LocationEvent event) {
        mCurrentLocation = event.getLocation();
        mAdapterNews.notifyDataSetChanged();
    }
}
