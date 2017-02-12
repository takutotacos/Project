package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Model.User;

/**
 * Created by sugitatakuto on 2017/02/12.
 */
public class AsyncAddFollowing extends AsyncTask<Void, Void, JSONObject> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String TAG = getClass().getName();
    private AsyncResponseJsonObject delegate;
    private String authToken;
    private List<User> users;
    private int position;
    private OkHttpClient client = new OkHttpClient();

    public AsyncAddFollowing(AsyncResponseJsonObject delegate, String authToken, List<User> users, int position) {
        this.delegate = delegate;
        this.authToken = authToken;
        this.users = users;
        this.position = position;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        String id = users.get(position).getId();
        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonResponse = null;
        String result = null;
        try {
            jsonRequest.put("id", id);
            RequestBody body = RequestBody.create(JSON, jsonRequest.toString());
            Request request = new Request.Builder().url(CommonConst.Api.ADD_TO_FOLLOWINGS).post(body)
                    .addHeader("Authorization", authToken).build();
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
