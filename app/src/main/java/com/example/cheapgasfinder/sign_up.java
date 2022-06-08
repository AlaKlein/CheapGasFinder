package com.example.cheapgasfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sign_up extends AppCompatActivity {

    private EditText ed_sign_up_username = null;
    private EditText getEd_sign_up_password = null;
    private EditText getEd_sign_up_password2 = null;
    private Button bt_submit = null;
    private Button bt_cancel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ed_sign_up_username = findViewById( R.id.ed_sign_up_username);
        getEd_sign_up_password = findViewById( R.id.ed_sign_up_password);
        getEd_sign_up_password2 = findViewById( R.id.ed_sign_up_password2);
        bt_submit = findViewById( R.id.bt_submit);
        bt_cancel = findViewById( R.id.bt_cancel);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}