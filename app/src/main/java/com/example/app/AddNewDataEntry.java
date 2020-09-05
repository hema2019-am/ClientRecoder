package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddNewDataEntry extends AppCompatActivity {

    EditText edt_clientNumber, edt_propertyName, edt_city, edt_area, edt_ownerName, edt_preferredLanguage;
    Button btn_submit, btn_validate;
    String clientNumber, ownerName, preferredLanguage;
    FirebaseDatabase mDb;
    DatabaseReference mRef;
    int submitted = 0, pending =0;



    Toolbar mAddClientToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_data_entry);

        getViews();
         mDb = FirebaseDatabase.getInstance();
         mRef = mDb.getReference().child("client");

        mAddClientToolbar = findViewById(R.id.addClientToolbar);
        setSupportActionBar(mAddClientToolbar);

        getSupportActionBar().setTitle("Add/Check Client");




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                String uid = null;
                if (currentUser != null) {
                    uid = currentUser.getUid();
                }
                clientNumber = edt_clientNumber.getText().toString();
                mRef = mDb.getReference().child("client").child(uid).child(clientNumber);
                ownerName = edt_ownerName.getText().toString();
                preferredLanguage = edt_preferredLanguage.getText().toString();
                if(TextUtils.isEmpty(ownerName)){
                    ownerName = "NA";
                }
                if(TextUtils.isEmpty(preferredLanguage)){
                    preferredLanguage = "NA";
                }


                HashMap<String, String> clientMap = new HashMap<>();
                clientMap.put("PropertyName", edt_propertyName.getText().toString());
                clientMap.put("ClientNumber", edt_clientNumber.getText().toString());
                clientMap.put("city", edt_city.getText().toString());
                clientMap.put("area", edt_area.getText().toString());
                clientMap.put("ownerName", ownerName);
                clientMap.put("preferredLanguage", preferredLanguage);
                clientMap.put("ApplicationStatus" , "Pending");
                clientMap.put("Comments", "");

                mRef.setValue(clientMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          submitted++;
                          pending++;
                          Toast.makeText(AddNewDataEntry.this, "PendingApproval", Toast.LENGTH_SHORT).show();
                          finish();


                      }
                      else {
                          Toast.makeText(AddNewDataEntry.this, "try Again", Toast.LENGTH_SHORT).show();
                      }
                    }
                });
            }
        });


        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                String uid = currentUser.getUid();
                clientNumber = edt_clientNumber.getText().toString();
                mRef = mDb.getReference().child("client").child(uid).child(clientNumber);
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String FirebaseClientNumber = snapshot.child("ClientNumber").getValue(String.class);
                        if(FirebaseClientNumber != null){
                            Toast.makeText(AddNewDataEntry.this, "data Already exists", Toast.LENGTH_SHORT).show();

                        }else{
                            edt_area.setVisibility(View.VISIBLE);
                            edt_city.setVisibility(View.VISIBLE);
                            edt_ownerName.setVisibility(View.VISIBLE);
                            edt_preferredLanguage.setVisibility(View.VISIBLE);
                            edt_propertyName.setVisibility(View.VISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);


                        }





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {



                    }
                });




            }
        });



    }

    private void getViews(){
        edt_area = findViewById(R.id.edt_area);
        edt_clientNumber = findViewById(R.id.edt_client_number);
        edt_ownerName = findViewById(R.id.edt_ownerName);
        edt_preferredLanguage = findViewById(R.id.edt_PreferLanguage);
        edt_propertyName = findViewById(R.id.edt_pgName);
        edt_city = findViewById(R.id.edt_city);
        btn_submit = findViewById(R.id.btn_submitEntry);
        btn_validate = findViewById(R.id.btn_validateNumber);
    }
}
