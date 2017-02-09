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
 * Created by sugitatakuto on 2017/01/23.
 */
public class AsyncUserExists extends AsyncTask<Void, Void, JSONObject> {

    private String TAG = getClass().getName();
    private String searchKey = null;
    private String searchValue = null;
    private AsyncResponseJsonObject delegate = null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncUserExists(AsyncResponseJsonObject delegate, String searchKey, String searchValue) {
        this.searchKey = searchKey;
        this.searchValue = searchValue;
        this.delegate = delegate;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonObject = new JSONObject();
        String result = null;
        JSONObject jsonData = null;
        try {
            jsonObject.put("searchKey", searchKey);
            jsonObject.put("searchValue", searchValue);
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // @Todo is this class even necessary???
        Request request = new Request.Builder().url(CommonConst.Api.SEARCH_USER).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            // @TODO: 2017/01/23 How to store search result and how to retrieve it as a boolean value?
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
            return null;
        }
        return jsonData;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        delegate.processFinish(result);
    }


}
