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

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Posting;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Interface.ApiService;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Manager.ApiManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShowImageActivity extends AppCompatActivity {
    private static final String TAG = ShowImageActivity.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private String authToken;
    private String postingId;
    private Bitmap decodedByte;
    private TextView textViewUserId;
    private TextView textViewCreateDate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", "");
        imageView = (ImageView) findViewById(R.id.show_image_image);
        textViewUserId= (TextView) findViewById(R.id.show_image_user_id);
        textViewCreateDate = (TextView) findViewById(R.id.show_image_create_date);
        Intent incomingIntent = getIntent();
        postingId = incomingIntent.getStringExtra("postingId");

        ApiService apiService = ApiManager.getApiService();
        Observable<Posting> posting = apiService.getPosting(authToken, postingId);
        posting.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Posting>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "ERROR : " + e.toString());
                    }

                    @Override
                    public void onNext(Posting posting) {
                        String content = posting.getContent();
                        String image = posting.getImage();
                        String id = posting.getId();
                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        textViewCreateDate.setText(id);
                        textViewCreateDate.setTextColor(Color.RED);
                        textViewUserId.setText(content);
                        textViewUserId.setTextColor(Color.RED);
                        imageView.setImageBitmap(decodedByte);
                    }
                });
    }
}
