package com.health.movieland;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmenttitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager){super(manager);}

    @Override
    public Fragment getItem(int position){return fragmentList.get(position);}

    @Override
    public int getCount(){return fragmentList.size();}

    public void addFrag(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmenttitle.add(title);

    }
    @Override
    public CharSequence getPageTitle(int position){return fragmenttitle.get(position);}
}
