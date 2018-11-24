package com.jawdata.hackjunction.jawdata.Utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.events.Event;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirebaseUtil {

    FirebaseDatabase database;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public FirebaseUtil() {
        database = FirebaseDatabase.getInstance();
    }

    public void PutFitbitData(Date date, int value) {
        DatabaseReference reference = database.getReference("Fitbit");
        reference.setValue(format.format(date),value);
    }

    public void FetchFitbitData() {
        DatabaseReference reference = database.getReference("Fitbit");
        reference.addListenerForSingleValueEvent(new FetchFromFirebaseListener());
    }
}
