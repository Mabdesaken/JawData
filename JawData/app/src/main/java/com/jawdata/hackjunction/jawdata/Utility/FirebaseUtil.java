package com.jawdata.hackjunction.jawdata.Utility;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jawdata.hackjunction.jawdata.Serializable.Fitbit;
import com.jawdata.hackjunction.jawdata.Serializable.JawData;
import com.jawdata.hackjunction.jawdata.Serializable.Recipe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseUtil {

    FirebaseDatabase database;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Response delegate;

    public FirebaseUtil(Response delegate) {
        database = FirebaseDatabase.getInstance();
        this.delegate = delegate;
    }

    public void PutFitbitData(Date date, int value) {
        DatabaseReference reference = database.getReference("Fitbit");
        reference.setValue(format.format(date),value);
    }

    public void fetchLatestFitbitData(int amount) {
        DatabaseReference reference = database.getReference("Fitbit");
        Query query = reference.orderByKey().limitToLast(amount);
        query.addListenerForSingleValueEvent(new FetchFromFirebaseListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Fitbit> values = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        values.add(snapshot.getValue(Fitbit.class));
                }
                delegate.getResponse(Fitbit.CMP,values);
            }
        });
    }

    /* FETCH JAW SENSOR DATA */
    public void fetchLatestSensorData(int amount) {
        DatabaseReference reference = database.getReference("sensorlogs3");
        Query query = reference.orderByKey().limitToLast(amount);
        query.addListenerForSingleValueEvent(new FetchFromFirebaseListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<JawData> jawDataList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        try {
                            JawData jawData;
                            jawData = new JawData(JawData.df.parse(child.getKey()), ((Long)child.getValue()).intValue());
                            jawDataList.add(jawData);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                delegate.getResponse(JawData.CMP,jawDataList);
            }
        });
    }

    public void fetchRelevantRecipe(double calories) {
        DatabaseReference reference = database.getReference("Recipes");
        Query query = reference.orderByChild("calories").startAt((int)calories).limitToFirst(1);
        query.addListenerForSingleValueEvent(new FetchFromFirebaseListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    delegate.getResponse(Recipe.CMP,recipe);
                }
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
