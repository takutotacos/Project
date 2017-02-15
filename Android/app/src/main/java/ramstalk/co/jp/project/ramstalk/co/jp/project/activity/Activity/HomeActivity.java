package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetRelationships;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener.ViewClickListener;

public class HomeActivity extends AppCompatActivity implements AsyncResponseJsonObject {
    private String TAG = CommonConst.ActivityName.TAG_HOME_ACTIVITY;
    private ImageButton addFollowingImgButton;
    private TextView followingsTextView, followersTextView;
    private Button followingsButton, followersButton;
    private SharedPreferences sharedPreferences;
    private String authToken = null;
    private AsyncGetRelationships mAsyncGetRealationships = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", "");
        addFollowingImgButton = (ImageButton) findViewById(R.id.home_add_following_button);
        followingsTextView = (TextView) findViewById(R.id.home_following_number_text_view);
        followersTextView = (TextView) findViewById(R.id.home_follower_number_text_view);
        followingsButton = (Button) findViewById(R.id.home_following_list_button);
        followersButton = (Button) findViewById(R.id.home_follower_list_button);
        mAsyncGetRealationships = new AsyncGetRelationships(this, authToken, CommonConst.ApiAction.GET_FOLLOWERS, true);
        mAsyncGetRealationships.execute();
        mAsyncGetRealationships = new AsyncGetRelationships(this, authToken, CommonConst.ApiAction.GET_FOLLOWINGS, true);
        mAsyncGetRealationships.execute();
        addFollowingImgButton.setOnClickListener(new ViewClickListener(HomeActivity.this, AddFollowingActivity.class));
        followingsButton.setOnClickListener(new ViewClickListener(HomeActivity.this, FollowingsListActivity.class));
        followersButton.setOnClickListener(new ViewClickListener(HomeActivity.this, FollowersListActivity.class));
    }

    @Override
    public void processFinish(JSONObject output) {
        if(output != null) {
            String number = null;
            try {
                String action = output.getString("action");
                if (CommonConst.ApiAction.GET_FOLLOWERS.equals(action)) {
                    number = CommonConst.ApiResponse.NULL.equals(output.getString("follower_numbers"))? "0" : output.getString("follower_numbers");
                    followersTextView.setText(number);
                } else {
                    number = CommonConst.ApiResponse.NULL.equals(output.getString("following_numbers"))? "0" : output.getString("following_numbers");
                    followingsTextView.setText(number);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
