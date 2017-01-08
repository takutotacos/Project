package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
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
    private GoogleMap mMap;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    /**
     *  if the user location or the location where the phone screen is at is needed,
     *  add the field to get from the MapsActivity when its initialized.
     */

    public AsyncGetInfoOnMap(Activity activity, GoogleMap mMap) {
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
        double latitude = 0.0;
        double longitude = 0.0;
        String comment = null;
        String createDate = null;
        String userId = null;
        System.out.println(result.length());
        for(int i = 0; i < result.length(); i++) {
            try {
                JSONObject data = result.getJSONObject(i);
                latitude = data.getDouble("latitude");
                longitude = data.getDouble("longitude");
                userId = data.getString("user_id");
                comment = data.getString("comment");
                createDate = data.getString("create_date");
            } catch(JSONException e) {
                Log.e(TAG, "JSON Exception happens: " + e.getCause());
            }
            LatLng data = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(data).title(comment));
        }
    }

}