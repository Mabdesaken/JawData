package com.jawdata.hackjunction.jawdata;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.jawdata.hackjunction.jawdata.Fragments.IntroPagerAdapter;

public class IntroView extends FragmentActivity {

    IntroPagerAdapter mIntroPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_view);

        mIntroPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.intro_pager);
        mViewPager.setAdapter(mIntroPagerAdapter);

    }

    @Override
    public void onBackPressed(){
        if (mViewPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
        }
    }

}
