package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

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

/**
 * Created by sugitatakuto on 2017/01/28.
 */
public class AsyncGetImage extends AsyncTask<Void, Void, JSONObject> {
    private final String TAG = this.getClass().getName();
    private AsyncResponseJsonObject delegate = null;
    private OkHttpClient client;
    private String postingId =null;
    private String url = CommonConst.Api.GET_A_DETAILED_POSTING;
    private String token = null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public AsyncGetImage(AsyncResponseJsonObject delegate, String postingId, String token) {
        this.delegate = delegate;
        this.postingId = postingId;
        this.url += "/" + postingId;
        this.token = token;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonResultData = null;
        String result = null;
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get().addHeader("Authorization", token).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
            jsonResultData = new JSONObject(result);
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
            return null;
        }
        catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        return jsonResultData;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        delegate.processFinish(result);
    }
}
