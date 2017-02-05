package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncLogin;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncRegistUser;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponse;

import static android.text.TextUtils.isEmpty;

public class RegistUserActivity extends AppCompatActivity implements AsyncResponse {
    private EditText mEditTextUserId, mEditTextPassword, mEditTextPasswordConfirmation, mEditTextEmail;

    private static String TAG = CommonConst.ActivityName.TAG_REGIST_USER_ACTIVITY;
    private AsyncRegistUser mAuthTask_regist = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private AsyncLogin mAuthTask = null;

    View focusView = null;
    String userId = null;
    String password = null;
    String passwordConfirmation = null;
    String email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_user);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

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
                        registUser(userId, email, password, passwordConfirmation);
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
        mEditTextPasswordConfirmation = (EditText)findViewById(R.id.editTextPasswordConfirm);
        mEditTextEmail = (EditText)findViewById(R.id.editTextEmail);

        userId = mEditTextUserId.getText().toString();
        password = mEditTextPassword.getText().toString();
        passwordConfirmation = mEditTextPasswordConfirmation.getText().toString();
        email = mEditTextEmail.getText().toString();
        return isDataValid(userId,email,password, passwordConfirmation);
    }

    private void registUser(final String userId, final String email, String password, String passwordConfirmation) throws Exception {
        mAuthTask_regist = new AsyncRegistUser(this,userId, email, password, passwordConfirmation);
        mAuthTask_regist.execute();
    }

    @Override
    public void processFinish(JSONObject output) {
        if(output != null) {
            try {
                if(CommonConst.ApiAction.CREATE.equals(output.getString("action"))) { // when creating a user
                    if(CommonConst.ApiResponse.REGISTER_SUCCESSFUL.equals(output.getString("status"))) {
                        mAuthTask = new AsyncLogin(this, email, password, null, false);
                        mAuthTask.execute();
                    } else {
                        String errorUserId = output.getJSONObject("errors").getString("user_id");
                        String errorEmail = output.getJSONObject("errors").getString("email");
                        if(!"".equals(errorUserId)) {
                            Log.i(TAG, errorUserId);
                            mEditTextUserId.setError(getString(R.string.error_userId_exist));
                            focusView = mEditTextUserId;
                            focusView.requestFocus();
                        }
                        if(!"".equals(errorEmail)) {
                            Log.i(TAG, errorEmail);
                            mEditTextEmail.setError(getString(R.string.error_email_exist));
                            focusView = mEditTextEmail;
                            focusView.requestFocus();
                        }
                    }
                } else { // when logging in user
                    String auth_token =  output.getString("auth_token");
                    String userId = output.getString("id");
                    sharedPreferencesEditor.putString("auth_token", auth_token);
                    sharedPreferencesEditor.putString("user_sid", userId);
                    sharedPreferencesEditor.apply();
                    finish();
                    proceedToActivity(MainActivity.class);
                }
            } catch(JSONException e) {
                Log.e(TAG, "JSON Exception happens: " + e.getCause());
            }
        } else {
            Log.e(TAG, "Null output is returned.");
            String message = getString(R.string.regist_failed);
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
            mEditTextPasswordConfirmation.setError(getString(R.string.error_invalid_passwordConfirm));
            focusView = mEditTextPasswordConfirmation;
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
