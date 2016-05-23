package com.app.ks.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.edit_text_username)
    protected EditText mEditTextUsername;
    @Bind(R.id.edit_text_password)
    protected EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private Callback<Void> getLoginCallback(final UserCredentails userCredentails) {
        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //status 2xx
                    MyAppApplication.setUserCredentials(userCredentails);
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //status 4xx lub 5xx
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //connection error
            }
        };
    }

    @OnClick(R.id.button_login)
    protected void buttonLoginOnClick() {
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            UserCredentails userCredentails = new UserCredentails(username, password);
            CRMService crmService = ServiceManager.getCrmService();
            Call<Void> loginCall = crmService.login(userCredentails);
            loginCall.enqueue(getLoginCallback(userCredentails));
        } else {
            Toast.makeText(MainActivity.this, "Enter data", Toast.LENGTH_SHORT).show();
        }


//        if(!username.isEmpty() && !password.isEmpty()) {
//            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//
//            intent.putExtra(IntentExtras.USERNAME, username);
//            intent.putExtra(IntentExtras.PASSWORD, password);
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(MainActivity.this,"Enter data",Toast.LENGTH_SHORT).show();
//        }
    }

}
