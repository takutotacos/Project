package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

/**
 * Created by shoji on 2017/01/28.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;

public class AsyncRegistUser extends AsyncTask<Void, Void, JSONObject> {
    private static String TAG = CommonConst.ActivityName.TAG_REGIST_USER_ACTIVITY;

    private AsyncResponseJsonObject delegate = null;
    private String mUserId;
    private String mEmail;
    private String mPassword;
    private String mPasswordConfirmation;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncRegistUser(AsyncResponseJsonObject delegate, String userId, String email, String password, String mPasswordConfirmation) {
        this.delegate = delegate;
        this.mUserId = userId;
        this.mEmail = email;
        this.mPassword = password;
        this.mPasswordConfirmation = mPasswordConfirmation;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject userObj = new JSONObject();
        JSONObject holder = new JSONObject();
        JSONObject jsonData = null;
        String result = null;
        try {
            userObj.put("user_id", mUserId);
            userObj.put("email", mEmail);
            userObj.put("password", mPassword);
            userObj.put("password_confirmation", mPasswordConfirmation);
            holder.put("user", userObj);
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            // @TODO null is acceptable?
            return null;
        }
        RequestBody body = RequestBody.create(JSON, holder.toString());
        Request request = new Request.Builder().url(CommonConst.Api.REGISTER_USER).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
            jsonData = new JSONObject(result);
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
            return null;
        }
        catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        return jsonData;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        delegate.processFinish(result);
    }


}

