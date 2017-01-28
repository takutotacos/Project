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

public class AsyncSearchUser extends AsyncTask<Void, Void, JSONObject> {

    private static String TAG = CommonConst.ActivityName.TAG_REGIST_USER_ACTIVITY;

    private AsyncResponse delegate = null;
    private String mUserId;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncSearchUser(AsyncResponse delegate, String userId) {
        this.delegate = delegate;
        this.mUserId = userId;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonLoginObject = new JSONObject();
        JSONObject jsonData = null;
        String result = null;
        try {

            jsonLoginObject.put("userId", mUserId);

        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            // @TODO null is acceptable?
            return null;
        }
        RequestBody body = RequestBody.create(JSON, jsonLoginObject.toString());
        Request request = new Request.Builder().url(CommonConst.UrlForPhp.SEARCH_USER).post(body).build();
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
            // @TODO null is acceptable?
            return null;
        }
        return jsonData;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        delegate.processFinish(result);
    }



}