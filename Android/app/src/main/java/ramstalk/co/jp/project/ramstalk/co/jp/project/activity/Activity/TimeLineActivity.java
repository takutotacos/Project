package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter.TimeLineFragmentPagerAdapter;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Fragment.TimeLineFragment;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetCategories;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;

public class TimeLineActivity extends AppCompatActivity implements AsyncResponseJsonObject {
    private String TAG = getClass().getName();
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AsyncGetCategories mAsyncGetCategories;
    private SharedPreferences sharedPreferences;
    private TimeLineFragmentPagerAdapter adapter;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        mToolbar = (Toolbar)findViewById(R.id.time_line_toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("auth_token", "");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.time_line_viewpager);
        mAsyncGetCategories = new AsyncGetCategories(this, token);
        mAsyncGetCategories.execute();
    }

    @Override
    public void processFinish(JSONObject output) {
        adapter = new TimeLineFragmentPagerAdapter(getSupportFragmentManager());
        try {
            JSONArray categoriesArray = output.getJSONArray("categories");
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject category = categoriesArray.getJSONObject(i);
                String categoryName = category.getString("category_name");
                adapter.addFragment(TimeLineFragment.newInstance(android.R.color.holo_blue_bright, categoryName), categoryName);
            }
        } catch(JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        viewPager.setAdapter(adapter);
    }
}
