package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.AddNewDataEntry.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {

    Button btn_newEntry;
    TextView txt_number_submitted, txt_number_Pending, txt_number_approved, txt_bumber_rejected ;

    int pending = 0 , submitted  = 0, approv = 0, reject=0;
    ProgressDialog mProgress;

    Toolbar mToolbarDashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mToolbarDashBoard = findViewById(R.id.dashboardToolbar);

        setSupportActionBar(mToolbarDashBoard);

        getSupportActionBar().setTitle("DashBoard");

        txt_number_submitted = findViewById(R.id.number_submitted);
        txt_number_Pending = findViewById(R.id.number_approval);
        txt_bumber_rejected = findViewById(R.id.number_rejected);
        txt_number_approved = findViewById(R.id.number_approved);

        FirebaseAuth mAuth;
        String User_id;
        DatabaseReference mRef;
        FirebaseDatabase mDatabase;
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Please wait");
        mProgress.setCanceledOnTouchOutside(false);




        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        User_id= currentUser.getUid();
        mRef = mDatabase.getReference().child("client").child(User_id);

       mRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               mProgress.dismiss();
               String applicationStatus = snapshot.child("ApplicationStatus").getValue(String.class);

               if (applicationStatus != null) {
                   switch (applicationStatus) {
                       case "Pending":
                           submitted++;
                           pending++;
                           txt_number_submitted.setText(Integer.toString(submitted));
                           txt_number_Pending.setText(Integer.toString(pending));


                           break;
                       case "Approved":
                           approv++;
                           submitted++;
                           txt_number_approved.setText(Integer.toString(approv));
                           txt_number_submitted.setText(Integer.toString(submitted));

                           break;
                       case "Rejected":
                           reject++;
                           submitted++;
                           txt_bumber_rejected.setText(Integer.toString(reject));
                           txt_number_submitted.setText(Integer.toString(submitted));

                           break;
                   }
               }
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot snapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
mProgress.dismiss();
           }
       });

        btn_newEntry = findViewById(R.id.btn_new_data_entry);
        btn_newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), AddNewDataEntry.class);
                startActivity(newIntent);

            }
        });


    }


    }

