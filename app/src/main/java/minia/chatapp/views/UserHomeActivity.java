package minia.chatapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import minia.chatapp.Fragments.RoomsFragment;
import minia.chatapp.Fragments.SettingsFragment;
import minia.chatapp.Fragments.UniversityFragment;
import minia.chatapp.Fragments.UserHomeFragment;
import minia.chatapp.R;
import minia.chatapp.adapters.ViewPagerAdapter;

public class UserHomeActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        sharedPreferences=getSharedPreferences("myDetails", Context.MODE_PRIVATE);

        //  intialize Tool bar
        appBarLayout = findViewById(R.id.appBarLayout);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView = findViewById(R.id.sideMenu);
        drawerLayout = findViewById(R.id.homeDrawer);
        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager = findViewById(R.id.viewPager);

        setupViewPager(viewPager);
        initBottomNavicationBar();
        initDrawerLayout();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        UserHomeFragment userHomeFragment =new UserHomeFragment(sharedPreferences.getLong("facultyId", 1L));
        RoomsFragment roomsFragment =new RoomsFragment();
        SettingsFragment settingsFragment =new SettingsFragment();
        UniversityFragment universityFragment=new UniversityFragment();
        adapter.addFragment(userHomeFragment, getString(R.string.action_home));
        adapter.addFragment(roomsFragment, getString(R.string.action_chat));
        adapter.addFragment(settingsFragment, getString(R.string.action_settings));
        adapter.addFragment(universityFragment, getString(R.string.about_uv));
        viewPager.setAdapter(adapter);
    }

    private void initBottomNavicationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_chat:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_settings:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.about_uv:
                        viewPager.setCurrentItem(3);
                        break;

                    default:
                        return false;
                }

                return true;
            }
        });
    }

    private void initDrawerLayout() {

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar, R.string.drawer_open, R.string.app_name)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getString(R.string.app_name));
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_chat:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_settings:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.about_uv:
                        viewPager.setCurrentItem(3);
                        break;


                    case R.id.action_about:
                        break;
                    case R.id.action_logout:
                        sharedPreferences.edit().putBoolean("isLogin", false).apply();
                        Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    default:
                        return false;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}

