package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetImage;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;

public class ShowImageActivity extends AppCompatActivity implements AsyncResponseJsonObject {
    private static final String TAG = CommonConst.ActivityName.TAG_SHOW_IMAGE_ACTIVITY;
    private SharedPreferences sharedPreferences;
    private String token = null;
    private String postingId = null;
    private String imgInfo = null;
    private String comment = null;
    private Bitmap decodedByte = null;
    private TextView textViewUserId;
    private TextView textViewCreateDate;

    private ImageView imageView;
    AsyncGetImage mAsyncGetImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("auth_token", "");
        imageView = (ImageView) findViewById(R.id.show_image_image);
        textViewUserId= (TextView) findViewById(R.id.show_image_user_id);
        textViewCreateDate = (TextView) findViewById(R.id.show_image_create_date);
        Intent incomingIntent = getIntent();
        postingId = incomingIntent.getStringExtra("postingId");
        if (!"".equals(postingId)) {
            mAsyncGetImage = new AsyncGetImage(this, postingId, token);
            mAsyncGetImage.execute();
        }
    }

    @Override
    public void processFinish(JSONObject result) {
        if(result != null) {
            try {
                String location1 = result.getString("location1");
                comment = result.getString("comment");
                // @Todo Do some research what this does.
                String imgInfo = result.getString("image");
                byte[] decodedString = Base64.decode(imgInfo, Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
                textViewCreateDate.setText(location1);
                textViewCreateDate.setTextColor(Color.RED);
                textViewUserId.setText(comment);
                textViewUserId.setTextColor(Color.RED);
            } catch(JSONException e) {
                Log.e(TAG, "The Json Exception occured: " + e.getCause());
            }
        }
    }
}
