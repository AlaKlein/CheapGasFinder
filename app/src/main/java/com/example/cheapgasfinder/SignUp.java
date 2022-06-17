package com.example.cheapgasfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edSignUpEmail = null;
    private EditText edSignUpPassword = null;
    private EditText edSignUpPassword2 = null;
    private Button btSubmit = null;
    private Button btCancel = null;
    private TextView tvError = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        edSignUpEmail = findViewById(R.id.edSignUpEmail);
        edSignUpPassword = findViewById(R.id.edSignUpPassword);
        edSignUpPassword2 = findViewById(R.id.edSignUpPassword2);
        btSubmit = findViewById(R.id.btSubmit);
        btCancel = findViewById(R.id.btCancel);
        tvError = findViewById(R.id.tv_error2);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edSignUpEmail.getText().toString();
                String password = edSignUpPassword.getText().toString();
                String password2 = edSignUpPassword2.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)
                        || !TextUtils.isEmpty(password2)) {


                    mAuth.createUserWithEmailAndPassword(email, MainActivity.getMd5Hash(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                            }
                        }
                    });
                } else {
                    tvError.setText("ué");
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}