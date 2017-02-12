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
 * Created by sugitatakuto on 2017/02/09.
 */
public class AsyncGetPostingsByCategories extends AsyncTask<Void, Void, JSONObject> {
    private final String TAG = getClass().getName();
    private String token = null;
    private AsyncResponseJsonObject delegate = null;
    private String categoryId;
    private String userId;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncGetPostingsByCategories(AsyncResponseJsonObject delegate, String token, String categoryId, String userId) {
        this.delegate = delegate;
        this.token = token;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonQueryObject = new JSONObject();
        JSONObject jsonData = null;
        String result = null;
        RequestBody body = RequestBody.create(JSON, jsonQueryObject.toString());
        Request request = new Request.Builder().url(CommonConst.Api.GET_POSTING_BY_CATEGORIES + "?user_id = " + userId + "&category_id=" + categoryId).get().addHeader("Authorization", token).build();
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
