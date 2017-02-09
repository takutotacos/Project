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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetCategories;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncPosting;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;

import static android.text.TextUtils.isEmpty;
import static ramstalk.co.jp.project.R.id.button_getImage;
import static ramstalk.co.jp.project.R.id.button_postImages;

public class PostingActivity extends AppCompatActivity implements AsyncResponseJsonObject {
    //TODO:LocationActivityのOnPause等が必要
    private static final String TAG = CommonConst.ActivityName.TAG_POSTING_ACTIVITY;
    private AsyncPosting mAsyncPosting = null;
    private AsyncGetCategories mAsyncGetCategories = null;
    private SharedPreferences sharedPreferences;
    //投稿情報
    private String token = null;
    private String userId = null;
    private String image = null;
    private EditText commentView = null;
    private ImageView imageView;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String selectedCategoryId = null;

    private final int REQUEST_PERMISSION = 1000;
    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private static final int RESULT_PICK_LOCATIONINFO = 1002;
    private Button button_get,button_post;
    private TextView dcimPath;
    private Spinner mSpinner;
    private ArrayList<String>categoriesForSpinner = new ArrayList<String>();
    private HashMap<String, String> spinnerMap = new HashMap<String, String>();
    private ArrayAdapter<String> adapter = null;
    private static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("auth_token", "");
        mAsyncGetCategories = new AsyncGetCategories(this, token);
        mAsyncGetCategories.execute();
        mSpinner = (Spinner) findViewById(R.id.posting_categoies_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String selectedCategoryName = (String) spinner.getSelectedItem();
                selectedCategoryId = spinnerMap.get(selectedCategoryName);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        // Android 6, API 23以上でパーミッシンの確認
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        } else {
            getLocationInfo();
        }
        button_post = (Button) findViewById(button_postImages);
        button_post.setOnClickListener(new View.OnClickListener() {
            String comment = null;
            String userId = sharedPreferences.getString("user_id", "");
            @Override
            public void onClick(View v) {
                comment = commentView.getText().toString();
                //画像が選択されているか、コメントがあるかチェック
                if(isValidValue(comment) && isValidValue(image) && isValidValue(userId)) {
                    Log.d(TAG,"POSTED");
                    mAsyncPosting = new AsyncPosting(PostingActivity.this, image,
                            userId, comment, latitude, longitude, selectedCategoryId, token);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            // 既に許可している
            getLocationInfo();
        } else{
            // 拒否していた場合
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
                    image =  encodeBase64(bmp);
                    Log.d(TAG, "bmp encoded:" + image);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //LocationActivityから返り値を受け取る
        }else if(requestCode == RESULT_PICK_LOCATIONINFO && resultCode == Activity.RESULT_OK){
            Bundle savedData = resultData.getExtras();
            latitude = savedData.getDouble("latitude", 0.0);
            longitude = savedData.getDouble("longitude", 0.0);
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
            try {
                if(CommonConst.ApiAction.INDEX.equals(output.getString("action"))) {
                    // when retrieving the all available categories
                    JSONArray categoriesArray = output.getJSONArray("categories");
                    for(int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject category = categoriesArray.getJSONObject(i);
                        String categoryName = category.getString("category_name");
                        categoriesForSpinner.add(categoryName);
                        spinnerMap.put(categoryName, category.getString("id"));
                    }
                    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesForSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(adapter);
                } else {
                    // when registering a post
                    String status = output.getString("status");
                    if (CommonConst.ApiResponse.REGISTER_SUCCESSFUL.equals(status)) {
                        toast("登録したでー");
                        finish();
                        proceedToActivity(MainActivity.class);
                    } else {
                        Log.e(TAG, "登録に失敗しました。 status: " + status);
                        toast("失敗したでー");
                    }
                }
            } catch(JSONException e) {
                Log.e(TAG, "JSON Exception happens: " + e.getCause());
            }
        } else {
            Log.e(TAG, "Null output is returned.");
            toast("失敗したでー");
        }
    }

    private void proceedToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        Log.i(TAG, "The next activity is: " + activity);
        startActivity(intent);
    }
}
