package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import ramstalk.co.jp.project.R;

public class ShowImage extends AppCompatActivity {

    private String userId = null;
    private String createDate = null;
    private String imgInfo = null;
    private Bitmap decodedByte = null;
    private TextView textViewUserId;
    private TextView textViewCreateDate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        Intent incomingIntent = getIntent();
        imgInfo = incomingIntent.getStringExtra("imgInfo");
        if (!"".equals(imgInfo)) {
            // @Todo Do some research what this does.
            byte[] decodedString = Base64.decode(imgInfo, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView = (ImageView) findViewById(R.id.show_image_image);
            imageView.setImageBitmap(decodedByte);
//          mMap.addMarker(new MarkerOptions().position(data).title(comment).icon(BitmapDescriptorFactory.fromBitmap(decodedByte)));
        }
    }
}
