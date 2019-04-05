package com.game.senjad.sgame.results_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.senjad.base.fragment.BaseFragment;
import com.game.senjad.sgame.GameActivity;
import com.game.senjad.sgame.R;
import com.game.senjad.sgame.results_view.AllResultFragment;
import com.game.senjad.sgame.results_view.CitiesResultsFragment;
import com.game.senjad.sgame.results_view.ProvincesResultFragment;
import com.game.senjad.sgame.results_view.SchoolResultsFragment;

import java.util.ArrayList;
import java.util.List;


public class ResultsFragment extends BaseFragment {
    private GameActivity gameActivity;
    Toolbar toolbar;
    TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = (GameActivity) getActivity();
        gameActivity.setTitle("نتایج");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_results, container, false);
        gameActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setRotationY(180);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new AllResultFragment(),"کشوری");
//        viewPagerAdapter.addFragment(new ProvincesResultFragment(),"استانی");
//        viewPagerAdapter.addFragment(new CitiesResultsFragment(),"شهری");
//        viewPagerAdapter.addFragment(new SchoolResultsFragment(),"مدرسه");
//        viewPagerAdapter.addFragment(new ProvincesResultFragment(),"بین استانها");
        viewPager.setAdapter(viewPagerAdapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<String>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
