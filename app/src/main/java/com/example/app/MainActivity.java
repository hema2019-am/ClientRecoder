package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {


    Button btnGenerateOTP, btnSignIn;
    EditText etPhoneNumber, etOTP, etCountryCode;

    String phoneNumber, otp, countryCode;

    FirebaseAuth auth;
    Toolbar mMainToolbar;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser =auth.getCurrentUser();
        if(currentUser != null){
            Intent newIntent = new Intent(getApplicationContext(), homePAGE.class);
            startActivity(newIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        StartFirebaseLogin();

        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setTitle("Register");


        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //throw new RuntimeException("Test Crash"); // Force a crash
                countryCode = etCountryCode.getText().toString();
                if(!TextUtils.isEmpty(countryCode) ){
                    phoneNumber =  "+" +countryCode + etPhoneNumber.getText().toString();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,
                            30,
                            TimeUnit.SECONDS,
                            MainActivity.this,
                            mCallback
                    );
                }else{
                    Toast.makeText(MainActivity.this, "entry country code", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = etOTP.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                SigninWithPhone(credential);
            }
        });


    }

    private void SigninWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Intent homeIntent = new Intent(getApplicationContext(), homePAGE.class);
                           startActivity(homeIntent);
                       }else{
                           Toast.makeText(MainActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }

    private void StartFirebaseLogin(){
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(MainActivity.this, "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this, "verification failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(MainActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void findViews(){
        btnGenerateOTP = findViewById(R.id.btn_otp);
        btnSignIn = findViewById(R.id.btn_login);

        etPhoneNumber = findViewById(R.id.edt_phoneNumber);
        etOTP = findViewById(R.id.edt_enterOTP);
        etCountryCode = findViewById(R.id.countryCode);

        mMainToolbar = findViewById(R.id.mainToolbar);
    }
}
