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
 * Created by sugitatakuto on 2017/02/11.
 */
public class AsyncGetRelationships extends AsyncTask<Void, Void, JSONObject> {
    private String TAG = getClass().getName();
    private String authToken = null;
    private String target = null;
    boolean onlyNumberFlg = true;
    private AsyncResponseJsonObject delegate = null;
    private OkHttpClient client = new OkHttpClient();
    private String url = null;

    public AsyncGetRelationships(AsyncResponseJsonObject delegate, String authToken, String target, boolean onlyNumberFlg) {
        this.authToken = authToken;
        this.delegate = delegate;
        this.target =target;
        this.onlyNumberFlg = onlyNumberFlg;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        String result = null;
        JSONObject jsonResult = null;
        url = makeUrl(target, onlyNumberFlg);
        Request request = new Request.Builder().url(url)
                .get().addHeader("Authorization", authToken).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.i(TAG, result);
            response.body().close();
            jsonResult = new JSONObject(result);
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
            return null;
        }
        catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        return jsonResult;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        this.delegate.processFinish(result);
    }

    private static String makeUrl(String target, boolean onlyNumberFlg) {
        if(CommonConst.ApiAction.GET_FOLLOWERS == target) { // request for followers
            if(onlyNumberFlg) { // only numbers
                return CommonConst.Api.GET_FOLLOWER_NUMBERS;
            }
            // detailed information also needed
            return CommonConst.Api.GET_FOLLOWERS_WITH_DETAIL;
        }
        // request for followings
        if(onlyNumberFlg) {
            return CommonConst.Api.GET_FOLLOWING_NUMBERS;
        }
        return CommonConst.Api.GET_FOLLOWINGS_WITH_DETAIL;
    }

}
