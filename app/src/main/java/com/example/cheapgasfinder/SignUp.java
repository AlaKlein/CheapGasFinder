package com.example.cheapgasfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    private EditText edSignUpUsername = null;
    private EditText edSignUpPassword = null;
    private EditText edSignUpPassword2 = null;
    private Button btSubmit = null;
    private Button btCancel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edSignUpUsername = findViewById( R.id.edSignUpUsername);
        edSignUpPassword = findViewById( R.id.edSignUpPassword);
        edSignUpPassword2 = findViewById( R.id.edSignUpPassword2);
        btSubmit = findViewById( R.id.btSubmit);
        btCancel = findViewById( R.id.btCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}