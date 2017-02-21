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
 * Created by sugitatakuto on 2017/02/18.
 */
public class AsyncLike extends AsyncTask<Void, Void, JSONObject> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String TAG = getClass().getName();
    private String authToken;
    private String postingId;
    private boolean isLike;
    private String likeId;
    private AsyncResponseJsonObject delegate;
    private OkHttpClient client = new OkHttpClient();

    public AsyncLike(AsyncResponseJsonObject delegate, String authToken, String postingId,
                     boolean isLike, String likeId) {
        this.delegate = delegate;
        this.authToken = authToken;
        this.postingId = postingId;
        this.isLike = isLike;
        this.likeId = likeId;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonResponse;
        String result;
        try {
            RequestBody body = RequestBody.create(JSON, jsonRequest.toString());
            Request request;
            if(isLike) {
                request = new Request.Builder().url(CommonConst.Api.LIKE_A_POSTING(postingId))
                        .post(body)
                        .addHeader("Authorization", authToken)
                        .build();
            } else {
                request = new Request.Builder().url(CommonConst.Api.DELETE_A_LIKE_FROM_POSTING(postingId, likeId))
                        .delete(body)
                        .addHeader("Authorization", authToken)
                        .build();
            }
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
            jsonResponse = new JSONObject(result);
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
            return null;
        }
        catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        return jsonResponse;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        this.delegate.processFinish(result);
    }
}
