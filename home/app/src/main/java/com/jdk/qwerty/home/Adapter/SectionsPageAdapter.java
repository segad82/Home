package com.jdk.qwerty.home.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 02/12/2017.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    //List per Fragment and FragmentTitle
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    //addFragment method called on MainActivity
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        //Tab's title
        fragmentTitleList.add(title);
    }

    //Generated with override method: -->
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    // <--
}

