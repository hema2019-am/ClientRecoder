package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SingleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView propertyName, location, locality, ownerNumber, ownerName, preferredLanguage, applicationStatus, Singlecomments;
    Button callOwner, Submit;
    EditText edt_comments;
    Spinner spinnerStatus;
    String[] Status = {"Pending", "Approved", "Rejected"};
    String SingleComments, SinglePropertyName, SingleLocation, SingleLocality, SingleOnerNumber, SingleOwnerName, SinglePreferredLanguage, SingleApplication, positionKey, StatusSpinner, comments, currentUser;
    FirebaseAuth mAuth;
    DatabaseReference mRef;

    Toolbar mSingleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        getViews();



        SinglePropertyName = getIntent().getStringExtra("propertyName");
        SingleLocation = getIntent().getStringExtra("location");
        SingleLocality = getIntent().getStringExtra("locality");
        SingleOwnerName = getIntent().getStringExtra("ownerName");
        SingleOnerNumber = getIntent().getStringExtra("ownerNumber");
        SinglePreferredLanguage = getIntent().getStringExtra("preferredLanguage");
        SingleApplication = getIntent().getStringExtra("applicationStatus");
        positionKey = getIntent().getStringExtra("key");
        SingleComments = getIntent().getStringExtra("comments");



        mSingleToolbar = findViewById(R.id.singleClientToolbar);
        setSupportActionBar(mSingleToolbar);

        getSupportActionBar().setTitle(SingleOwnerName);

        propertyName.setText("Property Name: " + SinglePropertyName);
        location.setText("Location: " + SingleLocation);
        locality.setText("Locality: " + SingleLocality);
        ownerName.setText("Owner Name: " + SingleOwnerName);
        ownerNumber.setText("Owner Number: " + SingleOnerNumber);
        preferredLanguage.setText("Preferred Language: "+ SinglePreferredLanguage);
        applicationStatus.setText("Application Status: " + SingleApplication);
        if(!SingleComments.equals("")){
            Singlecomments.setVisibility(View.VISIBLE);
            Singlecomments.setText("Comments: " + SingleComments);
        }

        callOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(Build.VERSION.SDK_INT>22){
                        if(ActivityCompat.checkSelfPermission(SingleActivity.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions( SingleActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                            return;
                        }
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+ SingleOnerNumber));
                        startActivity(callIntent);
                    }else{
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+ SingleOnerNumber));
                        startActivity(callIntent);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();

                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Status );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments = edt_comments.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser().getUid();
                mRef = FirebaseDatabase.getInstance().getReference().child("client").child(currentUser).child(positionKey);
                HashMap<String, String> Customer = new HashMap<>();

                Customer.put("PropertyName", SinglePropertyName);
                Customer.put("ClientNumber", SingleOnerNumber);
                Customer.put("city", SingleLocation);
                Customer.put("area", SingleLocality);
                Customer.put("ownerName", SingleOwnerName);
                Customer.put("preferredLanguage", SinglePreferredLanguage);
                Customer.put("ApplicationStatus" , StatusSpinner);
                Customer.put("Comments",comments );

                mRef.setValue(Customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SingleActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(SingleActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }

    private void getViews(){
        propertyName = findViewById(R.id.propertySingleName);
        location = findViewById(R.id.SingleLocation);
        locality = findViewById(R.id.SingleLocality);
        ownerNumber = findViewById(R.id.SingleOwnerNumber);
        ownerName = findViewById(R.id.SingleOwnerName);
        preferredLanguage = findViewById(R.id.SinglePreferredLanguage);
        applicationStatus = findViewById(R.id.SingleApplicationStatus);
        callOwner = findViewById(R.id.callOwner);
        edt_comments = findViewById(R.id.edt_comments);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        Submit = findViewById(R.id.submit);
        Singlecomments = findViewById(R.id.SingleComments);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        StatusSpinner = Status[position];
        Toast.makeText(this, "Application Status: " + Status[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
