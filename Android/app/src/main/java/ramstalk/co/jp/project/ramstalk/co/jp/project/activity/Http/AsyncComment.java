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
 * Created by sugitatakuto on 2017/02/20.
 */
public class AsyncComment extends AsyncTask<Void, Void, JSONObject> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String TAG = getClass().getName();
    private String authToken;
    private String postingId;
    private AsyncResponseJsonObject delegate;
    private String commentInput;
    private boolean createCommentFlg;
    private OkHttpClient client = new OkHttpClient();

    public AsyncComment(AsyncResponseJsonObject delegate, String authToken,
                        String postingId, String commentIntput, boolean createCommentFlg) {
        this.delegate = delegate;
        this.authToken = authToken;
        this.postingId = postingId;
        this.commentInput = commentIntput;
        this.createCommentFlg = createCommentFlg;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonResponse;
        String result;
        try {
            Request request;
            if(createCommentFlg) {
                JSONObject jsonRequest = new JSONObject();
                JSONObject holder = new JSONObject();
                jsonRequest.put("content", commentInput);
                holder.put("comment", jsonRequest);
                RequestBody body = RequestBody.create(JSON, holder.toString());
                request = new Request.Builder().url(CommonConst.Api.CREATE_A_COMMENT(postingId))
                        .post(body)
                        .addHeader("Authorization", authToken)
                        .build();
            } else {
                request = new Request.Builder().url(CommonConst.Api.GET_COMMENTS(postingId))
                        .get()
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
