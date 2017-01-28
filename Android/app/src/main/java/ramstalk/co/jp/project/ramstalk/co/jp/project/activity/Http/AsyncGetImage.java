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
public class AsyncGetImage extends AsyncTask<Void, Void, JSONObject> {
    private static final String URL = CommonConst.UrlForPhp.GET_IMAGE_DETAILED_DATA;
    private final String TAG = this.getClass().getName();
    private AsyncResponse delegate = null;
    private OkHttpClient client;
    private String createDate = null;
    private String userId =null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public AsyncGetImage(AsyncResponse delegate, String createDate, String userId) {
        this.delegate = delegate;
        this.createDate = createDate;
        this.userId = userId;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonRequestInfo = new JSONObject();
        JSONObject jsonResultData = null;
        String result = null;
        client = new OkHttpClient();
        try {
            jsonRequestInfo.put("createDate", createDate);
            jsonRequestInfo.put("userId", userId);
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            // @TODO null is acceptable?
            return null;
        }
        RequestBody body = RequestBody.create(JSON, jsonRequestInfo.toString());
        Request request = new Request.Builder().url(CommonConst.UrlForPhp.GET_IMAGE_DETAILED_DATA)
                .post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
        }
        try {
            jsonResultData = new JSONObject(result);
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            // @TODO null is acceptable?
            return null;
        }
        return jsonResultData;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        delegate.processFinish(result);
    }
}
