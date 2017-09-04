package com.example.denis.miningproject20.views.activities;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.views.adapters.SectionsPagerAdapter;
import com.example.denis.miningproject20.views.fragments.PlaceHolderFragment;
import com.example.denis.miningproject20.views.presenters.IPresenterStartActivity;
import com.example.denis.miningproject20.views.presenters.PresenterStartActivity;

import java.util.List;

public class StartActivity extends AppCompatActivity
        implements PresenterStartActivity.IStartActivity{

    private static final String LOG_TAG = "MY_LOG: " + StartActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private PresenterStartActivity mPresenter;
    private FloatingActionButton fab;
    private PlaceHolderFragment currentFragment;
    private int positionInFragment;
    private List<ResponseEthermine> responses;
    private List<ResponseEthermine> lastResponsesEthermine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = PresenterStartActivity.createInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unBindService();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public PresenterStartActivity getPresenter() {
        return mPresenter;
    }

    void toAttachFragment(PlaceHolderFragment currentFragment, int position){
        this.currentFragment = currentFragment;
        this.positionInFragment = position;
    }

    public void setlastResponsesEthermine(List<ResponseEthermine> lastResponsesEthermine) {
        this.lastResponsesEthermine = lastResponsesEthermine;
    }
}
