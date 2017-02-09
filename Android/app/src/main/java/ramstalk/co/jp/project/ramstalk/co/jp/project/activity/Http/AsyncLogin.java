package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

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

/**
 * Created by sugitatakuto on 2016/12/02.
 */
public class AsyncLogin extends AsyncTask<Void, Void, JSONObject> {

    private static String TAG = CommonConst.ActivityName.TAG_LOGIN_ACTIVITY;

    private AsyncResponseJsonObject delegate = null;
    private String mEmail;
    private String mPassword;
    private String mUserToken;
    private boolean isNativeLogin;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncLogin(AsyncResponseJsonObject delegate, String email, String password, String mUserToken, boolean isNativeLogin) {
        this.delegate = delegate;
        this.mEmail = email;
        this.mPassword = password;
        this.mUserToken = mUserToken;
        this.isNativeLogin = isNativeLogin;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonLoginObject = new JSONObject();
        JSONObject jsonData = null;
        String result = null;
        try {
            jsonLoginObject.put("email", mEmail);
            jsonLoginObject.put("isNativeLogin", isNativeLogin);
            if(isNativeLogin) {
                jsonLoginObject.put("password", mPassword);
            } else {
                jsonLoginObject.put("userToken", mUserToken);
            }
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        RequestBody body = RequestBody.create(JSON, jsonLoginObject.toString());
        Request request = new Request.Builder().url(CommonConst.Api.LOGIN).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
        }
        try {
            jsonData = new JSONObject(result);
        } catch(JSONException e) {
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
