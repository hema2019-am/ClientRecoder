package com.example.app;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView txtPropertyName, txtLocation, txtLocality, txtOwnerName, txtOwnerNumber, txtPreferedLanguage, txtApplicationStatus, txtComments;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.rootList);
        txtPropertyName = itemView.findViewById(R.id.propertyName);
        txtLocation = itemView.findViewById(R.id.Location);
        txtLocality = itemView.findViewById(R.id.Locality);
        txtOwnerName = itemView.findViewById(R.id.OwnerName);
        txtOwnerNumber = itemView.findViewById(R.id.OwnerNumber);
        txtPreferedLanguage = itemView.findViewById(R.id.PreferredLanguage);
        txtApplicationStatus = itemView.findViewById(R.id.ApplicationStatus);
        txtComments = itemView.findViewById(R.id.Comments);

    }

    public void setTxtPropertyName(String propertyName){
        txtPropertyName.setText("Property Name: " + propertyName);
    }

    public void setTxtLocation(String location){
        txtLocation.setText("Location: "+ location);
    }

    public void setTxtLocality(String locality){
        txtLocality.setText("Locality: "+ locality);
    }

    public void setTxtOwnerName(String ownerName){
        txtOwnerName.setText("Owner Name: " + ownerName);
    }

    public void setTxtOwnerNumber(String ownerNumber){
        txtOwnerNumber.setText("Owner Number: " + ownerNumber);
    }

    public void setTxtPreferedLanguage(String preferedLanguage){
        txtPreferedLanguage.setText("Preferred Language: " + preferedLanguage);
    }

    public void setTxtApplicationStatus(String applicationStatus){
        txtApplicationStatus.setText("Application Status: " + applicationStatus);
    }


    public void setTxtComments(String comments){
        if(comments != null){

            txtComments.setVisibility(View.VISIBLE);
            txtComments.setText("Comments:" + comments);
        }


    }
}
