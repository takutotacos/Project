package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncRegistUser;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponse;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncSearchUser;

import static android.text.TextUtils.isEmpty;

public class RegistUserActivity extends AppCompatActivity implements AsyncResponse {
    private EditText mEditTextUserId, mEditTextPassword, mEditTextPasswordConfirm, mEditTextEmail;

    private static String TAG = CommonConst.ActivityName.TAG_REGIST_USER_ACTIVITY;
    private AsyncRegistUser mAuthTask_regist = null;
    private AsyncSearchUser mAuthTask_search = null;

    View focusView = null;
    String userId = null;
    String password = null;
    String passwordConfirm = null;
    String email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_user);

        TextView mTextSignIn = (TextView)findViewById(R.id.login_singin_link);
        mTextSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                proceedToActivity(LoginActivity.class);
            }
        });


        Button mRegistButton = (Button)findViewById(R.id.buttonRegist);
        mRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setUIComponent()) {//データチェック1
                    try {
                        searchUser(userId);//データチェック2
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean setUIComponent() {
        mEditTextUserId = (EditText)findViewById(R.id.editTextUserId);
        mEditTextPassword = (EditText)findViewById(R.id.editTextPassword);
        mEditTextPasswordConfirm = (EditText)findViewById(R.id.editTextPasswordConfirm);
        mEditTextEmail = (EditText)findViewById(R.id.editTextEmail);

        userId = mEditTextUserId.getText().toString();
        password = mEditTextPassword.getText().toString();
        passwordConfirm = mEditTextPasswordConfirm.getText().toString();
        email = mEditTextEmail.getText().toString();
        return isDataValid(userId,email,password,passwordConfirm);
    }


    private void registUser(final String userId, final String password, final String email) throws Exception {
        mAuthTask_regist = new AsyncRegistUser(this,userId, password, email);
        mAuthTask_regist.execute();
    }


    private void searchUser(final String userId) throws Exception {
        mAuthTask_search = new AsyncSearchUser(this,userId);
        mAuthTask_search.execute();
    }

    @Override
    public void processFinish(JSONObject output) {
        finish();
        if(output != null) {
            try {
                //登録からの返り値
                if("regist".equals(output.getString("type"))){
                    proceedToActivity(LoginActivity.class);

                    //検索からの返り値
                }else if("search".equals(output.getString("type"))){
                    if("OK".equals(output.getString("status"))) {
                        try {
                            registUser(userId, password, email);
                            proceedToActivity(LoginActivity.class);
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }else{
                        Log.d("debug","ERROR");
                        mEditTextUserId.setError(getString(R.string.error_invalid_userId_exist));
                        focusView = mEditTextUserId;
                        Toast.makeText(this, getString(R.string.error_invalid_userId_exist), Toast.LENGTH_LONG).show();
                    }
                }
            } catch(JSONException e) {
                Log.e(TAG, "JSON Exception happens: " + e.getCause());
            }

        } else {
            Log.e(TAG, "Null output is returned.");
            String message = getString(R.string.regist_failed);
            startActivity(getIntent());
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    private Boolean isDataValid(String userId,String email,String password,String passwordConfirm){
        if(isUserIdValid(userId)){
            return false;
        }
        if(isEmailValid(email)){
            return false;
        }
        if(isPasswordValid(password,passwordConfirm)){
            return false;
        }
        return true;
    }
    private boolean isPasswordValid(String password, String passwordConfirm){
        password.trim();
        passwordConfirm.trim();
        if(password.length() <= 3){
            mEditTextPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditTextPassword;
            return true;
        }
        if(isEmpty(password)){
            mEditTextPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditTextPassword;
            return true;
        }
        if(isEmpty(passwordConfirm)){
            mEditTextPasswordConfirm.setError(getString(R.string.error_invalid_passwordConfirm));
            focusView = mEditTextPasswordConfirm;
            return true;
        }
        if(!password.equals(passwordConfirm)){
            mEditTextPassword.setError(getString(R.string.error_invalid_passwordConfirm_not_match));
            focusView = mEditTextPassword;
            return true;
        }
        return false;
    }
    private boolean isUserIdValid(String userId){
        userId.trim();
        if(isEmpty(userId)){
            mEditTextUserId.setError(getString(R.string.error_invalid_userId));
            focusView = mEditTextUserId;
            return true;
        }
        return false;
    }
    private boolean isEmailValid(String email){
        email.trim();
        if(isEmpty(email)){
            mEditTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEditTextEmail;
            return true;
        }
        if(!email.contains("@")){
            mEditTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEditTextEmail;
            return true;
        }
        return false;
    }
    private void proceedToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        Log.i(TAG, "The next activity is: " + activity);
        startActivity(intent);
    }
}
