package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ramstalk.co.jp.project.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button toMapButton;
    private Button toPostingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toMapButton = (Button) findViewById(R.id.button_to_map);
        toPostingButton = (Button) findViewById(R.id.button_to_posting);
        toMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToActivity(MapsActivity.class);
            }
        });
        toPostingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToActivity(PostingActivity.class);
            }
        });
    }

    private void proceedToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        Log.i(TAG, "The next activity is: " + activity);
        startActivity(intent);
    }
}
