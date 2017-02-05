package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

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

    String searchKey = null;
    String searchValue = null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncUserExists(String searchKey, String searchValue) {
        this.searchKey = searchKey;
        this.searchValue = searchValue;
    }

    @Override
    public Boolean doInBackground(Void... params) {
        JSONObject jsonObject = new JSONObject();
        boolean result = false;
        try {
            jsonObject.put("searchKey", searchKey);
            jsonObject.put("searchValue", searchValue);
        } catch(JSONException e) {
            Log.e(TAG, "JSON Exception happens: " + e.getCause());
            return null;
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().url(CommonConst.UrlForPhp.USER_EXISTS_PHP).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            @// TODO: 2017/01/23 How to store search result and how to retrieve it as a boolean value?
            result = response.body().toString();
            Log.i(TAG, result);
            response.body().close();
        }catch(IOException e) {
            Log.e(TAG, "IO Exception happens: " + e.getCause());
        }
        return result;
    }

    @Override
    public Boolean onPostExecute(JSONObject result) {
        return result.getBoolean("email");
    }


}
