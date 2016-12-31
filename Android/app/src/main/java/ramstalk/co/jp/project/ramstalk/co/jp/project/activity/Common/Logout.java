package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.LoginActivity;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;

/**
 * Created by sugitatakuto on 2016/12/04.
 */
public class Logout extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Button logout;

    public Logout() {
        this.sharedPreferences = getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = sharedPreferences.edit();
        this.logout = (Button)findViewById(R.id.button_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesEditor.clear();
                sharedPreferencesEditor.commit();
                logout();
                finish();
            }
        });
    }

    private void logout() {
        System.out.println("Logging out");
        startActivity(new Intent(this, LoginActivity.class));
    }
}
