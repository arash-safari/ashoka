package com.game.senjad.sgame;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

import me.relex.circleindicator.CircleIndicator;

public class SplashActivity extends AppBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!SharedPreferenceUtils.getInstance(this).getToken().equals("")) {
            startActivity(MainActivity.class);
            finish();
        }

        setContentView(R.layout.activity_splash);
        ViewPager viewpager = findViewById(R.id.viewpager);
        CircleIndicator indicator = findViewById(R.id.indicator);
        SplashAdapter splashAdapter = new SplashAdapter(getSupportFragmentManager());
        viewpager.setAdapter(splashAdapter);
        indicator.setViewPager(viewpager);
    }
    class SplashAdapter extends FragmentPagerAdapter {

        public SplashAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new Splash1Fragment();
                    break;
                case 1:
                    fragment = new Splash2Fragment();
                    break;
                case 2:
                    fragment = new Splash3Fragment();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
