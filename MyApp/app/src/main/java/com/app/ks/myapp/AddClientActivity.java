package com.app.ks.myapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddClientActivity extends AppCompatActivity {

    @Bind(R.id.edit_text_name)
    EditText mEditTextName;
    @Bind(R.id.edit_text_address)
    EditText  mEditTextAddress;
    @Bind(R.id.edit_text_email)
    EditText mEditTextEmail;
    @Bind(R.id.edit_text_phone)
    EditText mEditTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        ButterKnife.bind(this);
    }

    private void createDialog(String messeage) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle(R.string.header_data_validation);
        builder.setMessage(String.format("%s %s", messeage, getResources().getString(R.string.text_data_validation)));
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private boolean dataValidation() {

        if(mEditTextName.getText().toString().isEmpty()) {
            createDialog(String.valueOf(getResources().getString(R.string.text_name)));
            return false;
        } else if (mEditTextAddress.getText().toString().isEmpty()) {
            createDialog(getResources().getString(R.string.text_address));
            return false;
        } else if(mEditTextEmail.getText().toString().isEmpty()) {
            createDialog(getResources().getString(R.string.text_email));
            return false;
        } else if(mEditTextPhone.getText().toString().isEmpty()) {
            createDialog(getResources().getString(R.string.text_phone));
            return false;
        }
        else return true;
    }

    @OnClick(R.id.button_apply)
    public void setButtonApplyClick() {

        Client client = new Client();

        client.setName(mEditTextName.getText().toString());
        client.setAddress(mEditTextAddress.getText().toString());
        client.setEmail(mEditTextEmail.getText().toString());
        client.setPhone(mEditTextPhone.getText().toString());

        if(dataValidation()) {
            Intent intent = new Intent();
            intent.putExtra(IntentExtras.CLIENT, client);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

}
