package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ramstalk.co.jp.project.R;

public class ShowImage extends AppCompatActivity {

    private String userId = null;
    private String createDate = null;
    private TextView textViewUserId;
    private TextView textViewCreateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        Intent incomingIntent = getIntent();
        userId = incomingIntent.getStringExtra("userId");
        createDate = incomingIntent.getStringExtra("createDate");
        textViewCreateDate = (TextView) findViewById(R.id.show_image_create_date_text);
        textViewUserId = (TextView) findViewById(R.id.show_image_user_id);
        textViewUserId.setText(userId);
        textViewCreateDate.setText(createDate);
    }
}
