package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;

/**
 * Created by sugitatakuto on 2017/02/04.
 */
public class AsyncGetCategories extends AsyncTask<Void, Void, JSONObject> {

    private String TAG = this.getClass().getName();
    private AsyncResponse delegate = null;
    private String token = null;
    private OkHttpClient client = new OkHttpClient();

    public AsyncGetCategories(AsyncResponse delegate, String token) {
        this.delegate = delegate;
        this.token = token;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonData = null;
        String result = null;
        Request request = new Request.Builder().url(CommonConst.Api.GET_ALL_CATEGORIES)
                .get().addHeader("Authorization", token).build();
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
        this.delegate.processFinish(result);
    }
}
