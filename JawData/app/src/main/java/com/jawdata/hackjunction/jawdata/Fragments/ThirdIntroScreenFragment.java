package com.jawdata.hackjunction.jawdata.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jawdata.hackjunction.jawdata.R;

public class ThirdIntroScreenFragment  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_thirdintroscreen,container,false);
        return rootView;
    }
}
