package com.jawdata.hackjunction.jawdata.Utility;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FetchFromFirebaseListener implements ValueEventListener {

    public FetchFromFirebaseListener() {}

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
        System.out.println("Details: " + databaseError.getDetails());
    }
}
