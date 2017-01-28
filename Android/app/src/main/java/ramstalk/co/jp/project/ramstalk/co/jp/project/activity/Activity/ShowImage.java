package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Intent;
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
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponse;

public class ShowImage extends AppCompatActivity implements AsyncResponse{
    private static final String TAG = CommonConst.ActivityName.TAG_SHOW_IMAGE_ACTIVITY;
    private String userId = null;
    private String createDate = null;
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
        imageView = (ImageView) findViewById(R.id.show_image_image);
        textViewUserId= (TextView) findViewById(R.id.show_image_user_id);
        textViewCreateDate = (TextView) findViewById(R.id.show_image_create_date);
        Intent incomingIntent = getIntent();
        createDate = incomingIntent.getStringExtra("createDate");
        userId = incomingIntent.getStringExtra("userId");
        if (!"".equals(createDate) && !"".equals(userId)) {
            mAsyncGetImage = new AsyncGetImage(this, createDate, userId);
            mAsyncGetImage.execute();
        }
    }

    @Override
    public void processFinish(JSONObject result) {
        if(result != null) {
            try {
                String imgInfo = result.getString("imgInfo");
                userId = result.getString("userId");
                createDate = result.getString("createDate");
                comment = result.getString("comment");
                // @Todo Do some research what this does.
                byte[] decodedString = Base64.decode(imgInfo, Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
                textViewCreateDate.setText(createDate);
                textViewCreateDate.setTextColor(Color.RED);
                textViewUserId.setText(userId);
                textViewUserId.setTextColor(Color.RED);
            } catch(JSONException e) {
                Log.e(TAG, "The Json Exception occured: " + e.getCause());
            }
        }
    }
}
