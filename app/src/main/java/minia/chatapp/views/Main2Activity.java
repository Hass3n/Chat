package minia.chatapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import minia.chatapp.Fragments.DataUser;
import minia.chatapp.Fragments.Dialogcustom;
import minia.chatapp.Fragments.StaffFragment;
import minia.chatapp.Fragments.UniversityFragment;
import minia.chatapp.Fragments.employeeFragment;
import minia.chatapp.R;
import minia.chatapp.adapters.ViewPagerAdapter;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mauth;
    DatabaseReference mDatabaseReference;
    TextView txt_name,txt_email;
    ViewPagerAdapter adapter;


    ImageView image;
    TabLayout tabMenu;
    ViewPager viewPager;
    NavigationView navigationView;
    ArrayList<DataUser>data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        mauth=FirebaseAuth.getInstance();


      // setSupportActionBar(toolbar);
        tabMenu = findViewById(R.id.tabMenu);
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabMenu.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);  // 0 = drink , 1=food




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");


    }
    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new StaffFragment(0L), "Staff");
        adapter.addFragment(new employeeFragment(0L), "Employee");

        viewPager.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         //Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        //---LOGGING OUT AND ADDING TIME_STAMP----
        if(item.getItemId()==R.id.logout){

            Dialogcustom dialogcustom=new Dialogcustom();
            dialogcustom.show(getSupportFragmentManager(),"dialog");
        }
        return true;
    }


    //--OPENING LOGIN ACTIVITY--
    private void startfn(){
        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mauth.getCurrentUser();
        if(user==null){


            startfn();



        }
        else{



            mDatabaseReference.child(user.getUid()).child("online").setValue("true");

            read_user();


        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment=null;
        FragmentManager fragmentManager=getSupportFragmentManager();


        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(Main2Activity.this, SettingActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(Main2Activity.this, UserActivity.class);
            startActivity(intent);




        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(Main2Activity.this, UserDetails.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_tools) {

            mDatabaseReference.child(mauth.getCurrentUser().getUid()).child("online").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        FirebaseAuth.getInstance().signOut();
                        startfn();
                    }
                    else{
                        Toast.makeText(Main2Activity.this, "Try again..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class MyListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    }



    // read user email ,name passw

    public void read_user()
    {

        txt_name = navigationView.getHeaderView(0).findViewById(R.id.txt_name);
        // txt_name.setText("hassan moon");
        txt_email = navigationView.getHeaderView(0).findViewById(R.id.txt_email);
        // txt_email.setText("hassan moon @gmail.com");
        image =navigationView.getHeaderView(0). findViewById(R.id.imageView);
       mDatabaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                data=new ArrayList<>();

               for (DataSnapshot dmo : dataSnapshot.getChildren()) {

                   DataUser data_p=dmo.getValue(DataUser.class);
                   String c_Name = data_p.getName();
                   String C_photo = data_p.getImage();
                   String email=data_p.getEmail();
                    data.add(new DataUser(c_Name,"",email,C_photo,""));
                    Log.e("name",c_Name+"");


               }


               for(int i=0;i<data.size();i++)
               {

                   final SharedPreferences sharedPreferences = getSharedPreferences("mydetails", MODE_PRIVATE);
                   String email = sharedPreferences.getString("email", null);
                   Log.e("mail",email+"");
                    String c_email=data.get(i).getEmail().toString();
                   if(email.equals(c_email)) {

                   String name=data.get(i).getName().toString();
                   txt_name.setText(name+"");
                    txt_email.setText(c_email+"");
                    String photo=data.get(i).getImage().toString();

                       if (photo.isEmpty()) {
                           // Picasso.get().load(R.drawable.ic_launcher_foreground).into( viewHolder.book_image);
                           image.setImageResource(R.drawable.user_img);
                       } else {
                           Picasso.with(Main2Activity.this).load(photo).into(image);
                       }


                     return;

                   }




               }





           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
             Toast.makeText(Main2Activity.this,databaseError.getMessage().toString(),Toast.LENGTH_LONG).show();
           }
       });




    }


}

