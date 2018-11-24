package com.jawdata.hackjunction.jawdata.Utility;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        reference.addListenerForSingleValueEvent(new FetchFromFirebaseListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }
        });
    }

    /*public void AddUser(final String username, final String passw) throws NoSuchAlgorithmException {
        final DatabaseReference ref = database.getReference("Users");
        ref.addListenerForSingleValueEvent(new FetchFromFirebaseListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(username).exists()) {
                    return;
                }
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    return;
                }
                md.update(passw.getBytes(StandardCharsets.UTF_8));
                byte[] digest = md.digest();

                String hex = String.format("%064x",new BigInteger(1,digest)))
                ref.child(username).setValue(hex);
            }
        });
    }

    public boolean login(final String username, final String passw){
        DatabaseReference ref = database.getReference("Users");
        ref.addListenerForSingleValueEvent(new FetchFromFirebaseListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(username).exists()){
                    return false;
                }
            }
        });
    }*/
}
