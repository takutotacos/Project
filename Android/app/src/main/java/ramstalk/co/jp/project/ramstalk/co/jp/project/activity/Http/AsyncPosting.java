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
public class AsyncPosting extends AsyncTask<Void, Void, JSONObject> {

    private static String TAG = CommonConst.ActivityName.TAG_POSTING_ACTIVITY;
    private String image = null;
    private String userId = null;
    private String comment = null;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String address = null;
    private String placeName = null;
    private String placeCategory = null;
    private String categoryId = null;
    private String token = null;
    private AsyncResponseJsonObject delegate = null;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public AsyncPosting(AsyncResponseJsonObject delegate, String image, String userId, String comment,
                        double latitude, double longitude, String address, String placeName,
                        String placeCategory, String categoryId, String token) {
        this.delegate = delegate;
        this.image = image;
        this.userId = userId;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.placeName = placeName;
        this.placeCategory = placeCategory;
        this.categoryId = categoryId;
        this.token = token;
    }

    @Override
    public JSONObject doInBackground(Void... params) {
        JSONObject jsonPostingObject = new JSONObject();
        JSONObject holder = new JSONObject();
        JSONObject jsonData = null;
        String result = null;
        try {
            jsonPostingObject.put("image", image);
            jsonPostingObject.put("user_id", userId);
            jsonPostingObject.put("comment", comment);
            jsonPostingObject.put("latitude", latitude);
            jsonPostingObject.put("longitude", longitude);
            jsonPostingObject.put("address", address);
            jsonPostingObject.put("placeName", placeName);
            jsonPostingObject.put("placeCategory", placeCategory);
            jsonPostingObject.put("category_id", categoryId);
            holder.put("posting", jsonPostingObject);
            RequestBody body = RequestBody.create(JSON, holder.toString());
            Request request = new Request.Builder().url(CommonConst.Api.MAKE_A_POST).post(body).addHeader("Authorization", token).build();
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
