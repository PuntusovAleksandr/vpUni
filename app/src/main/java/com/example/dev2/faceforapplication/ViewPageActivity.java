package com.example.dev2.faceforapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.example.dev2.faceforapplication.fragments.ButtonsFragment;
import com.example.dev2.faceforapplication.fragments.CallButtonsFragment;
import com.example.dev2.faceforapplication.fragments.ContactsFragment;
import com.example.dev2.faceforapplication.fragments.HistoryFragment;
import com.example.dev2.faceforapplication.fragments.InputPlaceFragment;
import com.example.dev2.faceforapplication.fragments.StartPageFragment;
import com.example.dev2.faceforapplication.otherActivity.SettingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import sipua.SipProfile;
import sipua.impl.DeviceImpl;
import sipua.impl.SipManager;

/**
 * The type View page activity.
 */
public class ViewPageActivity extends AppCompatActivity implements ActionBar.TabListener,
        ButtonsFragment.OnFragmentInteractionListener,
        CallButtonsFragment.OnFragmentInteractionListener,
        InputPlaceFragment.OnFragmentInteractionListener {

    private static final int PAGE_COUNT = 3;

    private Intent intent;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private List<Fragment> listFragments;
    private StartPageFragment startPageFragment;
    private HistoryFragment historyFragment;
    private ContactsFragment contactsFragment;
    //Sip settings
    private SipProfile mSipProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_view_page);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /**
         * Registration sip in asterisk
         * TODO now registration on 3001 only, need preferences ready to make reg with other users
         * */
        mSipProfile = new SipProfile();

        //initialize device
        DeviceImpl.GetInstance().initialize(this, mSipProfile);

        //sip profile settings
        mSipProfile.setLocalPort(5060);
        mSipProfile.setRemoteIp("192.168.88.100");
        mSipProfile.setRemotePort(5060);

        mSipProfile.setSipUserName("3006");
        mSipProfile.setSipPassword("3006");

        mSipProfile.setTransport("UDP");
        mSipProfile.setLocalIp(SipManager.getIPAddress(true));

        //yuppyy REGISTRATION on asterisk
        DeviceImpl.GetInstance().Register();
        /**
         * end of registration
         */
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        startPageFragment = StartPageFragment.newInstance();
        historyFragment =  HistoryFragment.newInstance();
        contactsFragment =ContactsFragment.newInstance();

        listFragments = new ArrayList<>();
        listFragments.add(startPageFragment);
        listFragments.add(historyFragment);
        listFragments.add(contactsFragment);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), listFragments);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_setting) {
            intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * The Fragment.
         */
        Fragment fragment = null;
        private List<Fragment> fragmentList;

        /**
         * Instantiates a new Sections pager adapter.
         *
         * @param fm the fm
         * @param fragmentList the fragment list
         */
        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).);
            if (position >= 0 && position < fragmentList.size()) {
                return fragmentList.get(position);
            }else
            return new Fragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
