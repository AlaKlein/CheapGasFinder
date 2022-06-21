package com.example.cheapgasfinder;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private CheckBox cbPassword = null;
    private ProgressBar signUpProgressBar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        edSignUpEmail = (EditText) findViewById(R.id.edSignUpEmail);
        edSignUpPassword = (EditText) findViewById(R.id.edSignUpPassword);
        edSignUpPassword2 = (EditText) findViewById(R.id.edSignUpPassword2);
        btSubmit = (Button) findViewById(R.id.btSignUp2);
        btCancel = (Button) findViewById(R.id.btCancel);
        cbPassword = (CheckBox) findViewById(R.id.cbPassword2);
        signUpProgressBar = (ProgressBar) findViewById(R.id.signUnProgressBar);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edSignUpEmail.getText().toString();
                String password = edSignUpPassword.getText().toString();
                String password2 = edSignUpPassword2.getText().toString();

                if (TextUtils.isEmpty(edSignUpEmail.getText().toString())) {
                    edSignUpEmail.setError("Field can't be empty!");
                } else if (TextUtils.isEmpty(edSignUpPassword.getText().toString())) {
                    edSignUpPassword.setError("Field can't be empty!");
                } else if (TextUtils.isEmpty(edSignUpPassword2.getText().toString())) {
                    edSignUpPassword2.setError("Field can't be empty!");
                } else if (!password.equals(password2)) {
                    Toast.makeText(SignUp.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
                } else {
                    signUpProgressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, MainActivity.getMd5Hash(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                signUpProgressBar.setVisibility(View.INVISIBLE);
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SignUp.this, "" + error, Toast.LENGTH_SHORT).show();
                                signUpProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cbPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbPassword.isChecked()) {
                    edSignUpPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edSignUpPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edSignUpPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edSignUpPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }
}