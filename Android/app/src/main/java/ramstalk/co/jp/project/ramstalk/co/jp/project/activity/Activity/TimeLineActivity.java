package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter.TimeLineFragmentPagerAdapter;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Categories;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Category;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Interface.ApiService;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Manager.ApiManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimeLineActivity extends AppCompatActivity {
    private String TAG = TimeLineActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private TimeLineFragmentPagerAdapter adapter;
    private String authToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        mToolbar = (Toolbar) findViewById(R.id.time_line_toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", "");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.time_line_viewpager);

        adapter = new TimeLineFragmentPagerAdapter(getSupportFragmentManager());
        ApiService apiService = ApiManager.getApiService();
        Observable<Categories> categories = apiService.getAllCategories(authToken);
        categories.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categories>() {
                    @Override
                    public void onCompleted() {
                        viewPager.setAdapter(adapter);
                        tabLayout = (TabLayout) findViewById(R.id.time_line_tab);
                        tabLayout.setupWithViewPager(viewPager);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "ERROR : " + e.toString());
                    }

                    @Override
                    public void onNext(Categories categories) {
                        for (Category category : categories.getCategories()) {
                            adapter.addFragment(category.getId(), category.getCategoryName());
                        }
                    }
                });

    }
}