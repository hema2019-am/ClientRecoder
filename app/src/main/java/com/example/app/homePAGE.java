package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class homePAGE extends AppCompatActivity {

    CircleImageView dataEntry, dataValidation;
    Toolbar toolbar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getViews();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dataEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash_intent = new Intent(getApplicationContext(), DashBoard.class);
                startActivity(dash_intent);
            }
        });

        dataValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent ValidateIntent = new Intent(getApplicationContext(), ValidateEntry.class);
               startActivity(ValidateIntent);
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus, menu);

        MenuItem getItem = menu.findItem(R.id.get_item);
        if (getItem != null) {
            Button button = (Button) getItem.getActionView();
            button.setText("Logout");
            button.setBackgroundColor(getResources().getColor(R.color.logOutColor));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(logout);
                }
            });
            //Set a ClickListener, the text,
            //the background color or something like that
        }

        return super.onCreateOptionsMenu(menu);
    }

    private void getViews(){
        dataEntry = findViewById(R.id.btn_dataEntry);
        dataValidation = findViewById(R.id.btn_dataValidation);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser =mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
    }
}
