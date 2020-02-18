package minia.chatapp.aboutUnversity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import minia.chatapp.R;


public class Universty extends AppCompatActivity {
    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universty);
        bt1=findViewById(R.id.un_se1);
        bt2=findViewById(R.id.un_se2);
        bt3=findViewById(R.id.un_se3);
        bt4=findViewById(R.id.un_se4);
        bt5=findViewById(R.id.un_se5);
        bt6=findViewById(R.id.un_se6);
        bt7=findViewById(R.id.un_se7);
        bt8=findViewById(R.id.plan);
        bt9=findViewById(R.id.un_model);
        bt10=findViewById(R.id.un_rebly);
        bt11=findViewById(R.id.un_phone_app);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/history2.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/values.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/vision.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://mc.minia.edu.eg/buildings";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://mc.minia.edu.eg/minialeaders/";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/Heads.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/modawana1.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/StrategicPlan.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/load.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/complain1,2.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });
        bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/mobile.aspx";
                startActivity(new Intent(Universty.this,web.class));
            }
        });




    }
}
