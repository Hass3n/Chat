package minia.chatapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import minia.chatapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SharedPreferences sharedPreferences = getSharedPreferences("myDetails", MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.contains("isLogin") && sharedPreferences.getBoolean("isLogin", false)){

                    if (sharedPreferences.contains("type") && sharedPreferences.getString("type", " ").equals("user"))
                    {
                        if (sharedPreferences.contains("universityId") && sharedPreferences.contains("facultyId")) {
                            startActivity(new Intent(SplashActivity.this, UserHomeActivity.class));
                        }else{
                            startActivity(new Intent(SplashActivity.this, SettingsActivity.class));
                        }
                    }else{
                        Intent intent=new Intent(SplashActivity.this, EmployeeHomeActivity.class);
                        startActivity(intent);
                    }

                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 3000);
    }
}
