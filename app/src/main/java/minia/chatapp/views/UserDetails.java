package minia.chatapp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import minia.chatapp.Fragments.ChatFragment;
import minia.chatapp.Fragments.FriendFragment;
import minia.chatapp.Fragments.RequestFragment;
import minia.chatapp.Fragments.UniversityFragment;
import minia.chatapp.R;


public class UserDetails extends AppCompatActivity {


    ViewPager viewPager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                  //  CreateDialog();

                    fragment=new RequestFragment();
                    break;

                case R.id.navigation_chat:
                    fragment=new ChatFragment();
                    break;

                case R.id.navigation_settings:
                    fragment=new FriendFragment();
                    break;

                case R.id.about_uv:
                    fragment=new UniversityFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fram,fragment).commit();
            return true;
        }
    };







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

       /* Dialogcustom dialogcustom=new Dialogcustom();
        dialogcustom.show(getSupportFragmentManager(),"Dialog_show");*/

        BottomNavigationView navView = findViewById(R.id.navigation);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navView.setSelectedItemId(R.id.navigation_home);




    }



}
