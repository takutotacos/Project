package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;

/**
 * Created by sugitatakuto on 2017/01/08.
 */
public class AsyncGetInfoOnMap extends AsyncTask<Void, Void, JSONArray> {
    private static String TAG = CommonConst.ActivityName.TAG_LOGIN_ACTIVITY;
    private String token = null;
    private AsyncResponseJsonArray delegate = null;
    private OkHttpClient client;
    /**
     *  if the user location or the location where the phone screen is at is needed,
     *  add the field to get from the MapsActivity when its initialized.
     */

    public AsyncGetInfoOnMap(AsyncResponseJsonArray delegate, String token) {
        this.delegate = delegate;
        this.token = token;
    }

    @Override
    public JSONArray doInBackground(Void... url) {
        JSONArray jsonData = null;
        String result = null;
        client = new OkHttpClient();
        Request request = new Request.Builder().url(CommonConst.Api.GET_ALL_POSTINGS_ON_MAP).get().addHeader("Authorization", token).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
            jsonData = new JSONArray(result);
        } catch (IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
            return null;
        }
        catch (JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        return jsonData;
    }

    @Override
    public void onPostExecute(JSONArray result) {
        delegate.processFinish(result);
    }
}