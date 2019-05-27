package com.gyh.wanandroid.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.base.gyh.baselib.base.BaseActivity;
import com.gyh.wanandroid.R;
import com.gyh.wanandroid.view.fragment.HomePageFragment;
import com.gyh.wanandroid.view.fragment.KnowledgeFragment;
import com.gyh.wanandroid.view.fragment.NavigationFragment;
import com.gyh.wanandroid.view.fragment.ProjectsFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FrameLayout mFrameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);
        BottomNavigationView mMainbottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomNavigationView);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mMainbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homgpage:
                        addFragment(HomePageFragment.class,R.id.main_frameLayout);
                        return true;
                    case R.id.projets:
                        addFragment(ProjectsFragment.class,R.id.main_frameLayout);

                        return true;
                    case R.id.knowledge:
                        addFragment(KnowledgeFragment.class,R.id.main_frameLayout);

                        return true;
                    case R.id.navigation:
                        addFragment(NavigationFragment.class,R.id.main_frameLayout);

                        return true;
                    default:
                        //nothing to do
                        break;
                }
                return false;
            }
        });

//        drawer.openDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
