package minia.chatapp.views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import minia.chatapp.R;
import minia.chatapp.models.Users;
import minia.chatapp.adapters.EmployeeRoomsAdapter;
import minia.chatapp.models.Room;

public class EmployeeHomeActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView roomsRecycler;
    DatabaseReference roomsDatabaseReference;
    DatabaseReference usersDatabaseReference;
    SharedPreferences sharedPreferences;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

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

        initDrawerLayout();


        roomsRecycler = findViewById(R.id.roomsRecycler);

        sharedPreferences = getSharedPreferences("myDetails", Context.MODE_PRIVATE);
        String employeeId = sharedPreferences.getString("id", "1");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        roomsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("rooms");
        roomsDatabaseReference.orderByChild("employeeId").equalTo(employeeId).addValueEventListener(listener);


    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        break;
                    case R.id.action_about:
                        break;
                    case R.id.action_logout:
                        sharedPreferences.edit().putBoolean("isLogin", false).apply();
                        Intent intent = new Intent(EmployeeHomeActivity.this, LoginActivity.class);
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

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            final ArrayList<Room> rooms = new ArrayList();
            for (DataSnapshot dmo : dataSnapshot.getChildren()){
                Room room = dmo.getValue(Room.class);
                room.setKey(dmo.getKey());
                rooms.add(room);
            }

            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            usersDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Users> users = new ArrayList<>();
                    for(DataSnapshot dmo : dataSnapshot.getChildren()){
                        Users user = dmo.getValue(Users.class);
                        user.setKey(dmo.getKey());
                        users.add(user);
                    }

                    for (int i=0; i < rooms.size(); i++){
                        for (int j = 0; j < users.size(); j++){
                            if (rooms.get(i).getUserId().equals(users.get(j).getKey())){
                                rooms.get(i).setUser(users.get(j));
                                break;
                            }
                        }
                    }

                    roomsRecycler.setLayoutManager(new LinearLayoutManager(EmployeeHomeActivity.this));
                    EmployeeRoomsAdapter adapter = new EmployeeRoomsAdapter(rooms, EmployeeHomeActivity.this);
                    roomsRecycler.setAdapter(adapter);

                    adapter.setOnItemClick(new EmployeeRoomsAdapter.OnItemClick() {
                        @Override
                        public void onItemClick(Room room, int postion) {
                            Intent intent = new Intent(EmployeeHomeActivity.this, EmployeeChatActivity.class);
                            intent.putExtra("roomKey", room.getKey());
                            intent.putExtra("userId", room.getUserId());
                            intent.putExtra("empId", room.getEmployeeId());
                            startActivity(intent);
                        }
                    });

                    mProgressDialog.dismiss();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mProgressDialog.dismiss();
                }
            });
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(EmployeeHomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }
    };
}

