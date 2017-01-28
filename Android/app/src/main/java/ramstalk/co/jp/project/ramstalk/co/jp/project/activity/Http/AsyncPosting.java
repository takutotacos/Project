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
 * Created by sugitatakuto on 2017/01/28.
 */
public class AsyncPosting extends AsyncTask<Void, Void, JSONObject> {

    private static String TAG = CommonConst.ActivityName.TAG_POSTING_ACTIVITY;
    private String imgInfo = null;
    private String userId = null;
    private String comment = null;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private AsyncResponse delegate = null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncPosting(AsyncResponse delegate, String imgInfo, String userId, String comment,
                        double latitude, double longitude) {
        this.delegate = delegate;
        this.imgInfo = imgInfo;
        this.userId = userId;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonLoginObject = new JSONObject();
        JSONObject jsonData = null;
        String result = null;
        try {
            jsonLoginObject.put("imgInfo", imgInfo);
            jsonLoginObject.put("userId", userId);
            jsonLoginObject.put("comment", comment);
            jsonLoginObject.put("latitude", latitude);
            jsonLoginObject.put("longitude", longitude);
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            // @TODO null is acceptable?
            return null;
        }
        RequestBody body = RequestBody.create(JSON, jsonLoginObject.toString());
        Request request = new Request.Builder().url(CommonConst.UrlForPhp.POSTING_INFO).post(body).build();
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