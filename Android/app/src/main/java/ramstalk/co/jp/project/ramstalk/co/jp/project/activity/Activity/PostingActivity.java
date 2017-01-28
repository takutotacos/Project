package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncPosting;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponse;

import static android.text.TextUtils.isEmpty;
import static ramstalk.co.jp.project.R.id.button_getImage;
import static ramstalk.co.jp.project.R.id.button_postImages;

public class PostingActivity extends AppCompatActivity implements AsyncResponse {
    //TODO:LocationActivityのOnPause等が必要
    private static final String TAG = CommonConst.ActivityName.TAG_POSTING_ACTIVITY;
    private AsyncPosting mAsyncPosting = null;
    private SharedPreferences sharedPreferences;
    //投稿情報
    private String userId = "test_user";
    private String imgInfo = null;
    private EditText commentView = null;
    private ImageView imageView;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private final int REQUEST_PERMISSION = 1000;
    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private static final int RESULT_PICK_LOCATIONINFO = 1002;
    private Button button_get,button_post;
    private TextView dcimPath;
    private static Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        dcimPath = (TextView) findViewById(R.id.text_view);
        // ギャラリーのパスを取得する
        dcimPath.setText("ギャラリーのPath:　" + getGalleryPath());

        imageView = (ImageView) findViewById(R.id.image_view);
        commentView = (EditText) findViewById(R.id.editTextComment);

        button_get = (Button) findViewById(button_getImage);
        button_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // it would be "*/*".
                intent.setType("image/*");

                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });

        button_post = (Button) findViewById(button_postImages);

        button_post.setOnClickListener(new View.OnClickListener() {
            String comment = null;
            String userId = sharedPreferences.getString("userId", "");
            @Override
            public void onClick(View v) {
                comment = commentView.getText().toString();
                //画像が選択されているか、コメントがあるかチェック
                if(isValidValue(comment) && isValidValue(imgInfo) && isValidValue(userId)) {
                    // Android 6, API 23以上でパーミッシンの確認
                    if (Build.VERSION.SDK_INT >= 23) {
                        checkPermission();
                    } else {
                        getLocationInfo();
                    }
                    Log.d(TAG,"come back");
                    Log.d(TAG,"POSTED");
                    mAsyncPosting = new AsyncPosting(PostingActivity.this, imgInfo,
                            userId, comment, latitude, longitude);
                    mAsyncPosting.execute();
                }else{
                    toast("投稿項目を入力してください もしくはユーザIDが取得できていません。管理者に連絡してください。");
                }
            }
        });
    }

    /*Permission関連*/
    // 位置情報許可の確認
    public void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getLocationInfo();
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(PostingActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);

        } else {
            toast("許可されないとアプリが実行できません");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_PERMISSION);
        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationInfo();


            } else {
                // それでも拒否された時の対応
                toast("APIへの許可が必要です");
            }
        }
    }

    //LocationActivityにintent投げる
    public void getLocationInfo(){
        Intent intent = new Intent(getApplication(), LocationActivity.class);
        int requestCode = RESULT_PICK_LOCATIONINFO;
        startActivityForResult( intent, requestCode );
    }

    private String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    imgInfo =  encodeBase64(bmp);
                    Log.d(TAG, "bmp encoded:" + imgInfo);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //LocationActivityから返り値を受け取る
        }else if(requestCode == RESULT_PICK_LOCATIONINFO && resultCode == Activity.RESULT_OK){
            System.out.println(resultData.getDoubleExtra("latitude",0.0));
            latitude = resultData.getDoubleExtra("latitude",0.0);
            longitude = resultData.getDoubleExtra("longitude",0.0);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private String encodeBase64(Bitmap bmp) {
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        return Base64.encodeToString(byteArrayBitmapStream.toByteArray(), Base64.DEFAULT);
    }


    protected Boolean isValidValue(String target){
        return target != null && !isEmpty(target);
    }

    public void toast(String message) {
        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void processFinish(JSONObject output) {
        if(output != null) {
            String httpStatus = null;
            try {
                httpStatus = output.getString("httpStatus");
            } catch(JSONException e) {
                Log.e(TAG, "JSON Exception happens: " + e.getCause());
            }
            toast("登録したでー");
        } else {
            Log.e(TAG, "Null output is returned.");
            toast("失敗したでー");
        }
    }
}
