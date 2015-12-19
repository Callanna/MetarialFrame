package com.github.callanna.metarialframe.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.callanna.metarialframe.R;
import com.github.callanna.metarialframe.base.BaseActivity;

import java.util.LinkedList;

public class MainActivity extends BaseActivity {

    private ListView mDrawerList;
    private View mDrawerHeaderView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected boolean setToolbarAsActionbar() {
        return true;
    }

    @Override
    protected void onBaseActivityCreated(Bundle savedInstanceState) {
        setMyContentView(R.layout.activity_main);

        initDarwer();
    }

    private void initDarwer() {
        mDrawerList = (ListView) findViewById(R.id.menu_drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        initDarwerHead();
    }

    private void initDarwerHead() {
        mDrawerHeaderView = getLayoutInflater().inflate(R.layout.drawerheader,null);
        mDrawerList.addHeaderView(mDrawerHeaderView);
    }

}
