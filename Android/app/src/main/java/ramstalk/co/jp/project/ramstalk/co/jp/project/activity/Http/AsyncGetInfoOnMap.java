package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

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
    private static String URL = CommonConst.UrlForPhp.GET_IMAGE_DATA;
    private Activity mapActivity;
    private AsyncResponseMap delegate = null;
    private GoogleMap mMap;
    private OkHttpClient client;
    /**
     *  if the user location or the location where the phone screen is at is needed,
     *  add the field to get from the MapsActivity when its initialized.
     */

    public AsyncGetInfoOnMap(AsyncResponseMap delegate, Activity activity, GoogleMap mMap) {
        this.delegate = delegate;
        this.mapActivity = activity;
        this.mMap = mMap;
    }

    @Override
    public JSONArray doInBackground(Void... url) {
        JSONArray jsonData = null;
        String result = null;
        client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).get().build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
        } catch (IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
        }

        try {
            jsonData = new JSONArray(result);
        } catch (JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
        }
        return jsonData;
    }

    @Override
    public void onPostExecute(JSONArray result) {
        delegate.processFinish(result);
    }
}