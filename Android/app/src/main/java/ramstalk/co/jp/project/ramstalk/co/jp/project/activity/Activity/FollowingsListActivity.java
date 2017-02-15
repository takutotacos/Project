package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter.UserAdapter;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncSearchUser;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Model.User;

public class FollowingsListActivity extends AppCompatActivity implements AsyncResponseJsonObject {
    private String TAG = CommonConst.ActivityName.TAG_LIST_FOLLOWINGS_ACTIVITY;
    private EditText editTextUserId;
    private ImageButton searchButton;
    private SharedPreferences sharedPreferences;
    private String authToken;
    private AsyncSearchUser mAsyncSearchUser;
    private ListView userList;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followings_list);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userList = (ListView) findViewById(R.id.user_list_layout).findViewById(R.id.user_list);
        editTextUserId = (EditText) findViewById(R.id.user_list_layout).findViewById(R.id.edit_text_user_id);
        searchButton = (ImageButton) findViewById(R.id.user_list_layout).findViewById(R.id.search_button);
        mAsyncSearchUser = new AsyncSearchUser(FollowingsListActivity.this, authToken, "", CommonConst.ApiAction.GET_FOLLOWINGS);
        mAsyncSearchUser.execute();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = editTextUserId.getText().toString();
                mAsyncSearchUser = new AsyncSearchUser(FollowingsListActivity.this, authToken, userId, CommonConst.ApiAction.GET_FOLLOWINGS);
                mAsyncSearchUser.execute();
            }
        });
    }

    @Override
    public void processFinish(JSONObject output) {
        if(output != null) {
            try {
                if(CommonConst.ApiAction.GET_FOLLOWINGS.equals(output.getString("action"))) { // when searching for users
                    List<User> users = new ArrayList<User>();
                    if (CommonConst.ApiResponse.EXISTS.equals(output.getString("status"))) {
                        JSONArray usersArray = output.getJSONArray("followings");
                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject userJsonObject = usersArray.getJSONObject(i);
                            User user = new User();
                            user.setId(userJsonObject.getString("id"));
                            user.setUserId(userJsonObject.getString("user_id"));
                            // @todo add image field to Posting and here
                            //user.setImage(userJsonObject.getString("comment"));
                            users.add(user);
                        }
                        UserAdapter adapter = new UserAdapter(
                                getBaseContext(), R.layout.user_list_item, users, authToken, FollowingsListActivity.this, CommonConst.ActivityName.TAG_LIST_FOLLOWINGS_ACTIVITY );
                        userList.setAdapter(adapter);
                    } else {
                        toast("ユーザが見つかりませんでした。");
                    }
                }
            } catch(JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void toast(String message) {
        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
