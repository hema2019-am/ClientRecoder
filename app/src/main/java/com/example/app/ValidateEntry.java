package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ValidateEntry extends AppCompatActivity {
    public RecyclerView mCustomerListList;

    public DatabaseReference mUserDatabase;
    public FirebaseAuth mAuth;
    String mUserId;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    ProgressDialog mProgressDialog;


    Toolbar mtoolbarValidate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_entry);

        mCustomerListList = findViewById(R.id.recyclerView);

        mtoolbarValidate = findViewById(R.id.usersToolbar);
        setSupportActionBar(mtoolbarValidate);

        getSupportActionBar().setTitle("Client");


        linearLayoutManager = new LinearLayoutManager(this);
        mCustomerListList.setLayoutManager(linearLayoutManager);
        mCustomerListList.setHasFixedSize(true);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        fetch();

    }

    private void fetch() {
        mProgressDialog.dismiss();
        mAuth = FirebaseAuth.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("client").child(mUserId);
        Query query = mUserDatabase;
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(query, new SnapshotParser<Model>() {
                    @NonNull
                    @Override
                    public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Model(snapshot.child("PropertyName").getValue().toString(),
                                snapshot.child("city").getValue().toString(),
                                snapshot.child("area").getValue().toString(),
                                snapshot.child("ClientNumber").getValue().toString(),
                                snapshot.child("ownerName").getValue().toString(),
                                snapshot.child("preferredLanguage").getValue().toString(),
                                snapshot.child("ApplicationStatus").getValue().toString(),
                                snapshot.child("Comments").getValue().toString()
                        );


                    }
                }).build();

        adapter = new FirebaseRecyclerAdapter<Model, MyViewHolder>(options) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_property_layout, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Model model) {
                holder.setTxtPropertyName(model.getPropertyName());
                holder.setTxtLocation(model.getLocation());
                holder.setTxtLocality(model.getLocality());
                holder.setTxtOwnerNumber(model.getOwnerNumber());
                holder.setTxtOwnerName(model.getOwnerName());
                holder.setTxtPreferedLanguage(model.getPreferedLanguage());
                holder.setTxtApplicationStatus(model.getApplicationStatus());
                if (!model.getComments().equals("")) {
                    holder.setTxtComments(model.getComments());
                }


                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleActivity = new Intent(getApplicationContext(), SingleActivity.class);
                        singleActivity.putExtra("propertyName", model.getPropertyName());
                        singleActivity.putExtra("ownerNumber", model.getOwnerNumber());
                        singleActivity.putExtra("location", model.getLocation());
                        singleActivity.putExtra("locality", model.getLocality());
                        singleActivity.putExtra("ownerName", model.getOwnerName());
                        singleActivity.putExtra("preferredLanguage", model.getPreferedLanguage());
                        singleActivity.putExtra("applicationStatus", model.getApplicationStatus());
                        singleActivity.putExtra("comments", model.getComments());
                        singleActivity.putExtra("key", getRef(position).getKey());

                        startActivity(singleActivity);
                    }
                });
            }


        };

        mCustomerListList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}


