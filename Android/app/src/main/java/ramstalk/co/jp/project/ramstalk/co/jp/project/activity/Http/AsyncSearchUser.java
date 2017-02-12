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
import okhttp3.Response;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;

public class AsyncSearchUser extends AsyncTask<Void, Void, JSONObject> {
    private String TAG = getClass().getName();
    private AsyncResponseJsonObject delegate = null;
    private String mUserId;
    private String authToken;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncSearchUser(AsyncResponseJsonObject delegate, String authToken, String userId) {
        this.delegate = delegate;
        this.mUserId = userId;
        this.authToken = authToken;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonData = null;
        String result = null;
        Request request = new Request.Builder().url(CommonConst.Api.SEARCH_USER + "?user_id=" +mUserId).get().addHeader("Authorization", authToken).build();
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