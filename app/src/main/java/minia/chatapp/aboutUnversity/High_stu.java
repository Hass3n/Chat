package minia.chatapp.aboutUnversity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import minia.chatapp.R;

public class High_stu extends AppCompatActivity {
   Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_stu);
        btn1=findViewById(R.id.high_se1);
        btn2=findViewById(R.id.high_se2);
        btn3=findViewById(R.id.high_se3);
        btn4=findViewById(R.id.high_se4);
        btn5=findViewById(R.id.high_se5);
        btn6=findViewById(R.id.high_se6);
        btn7=findViewById(R.id.high_se7);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://misdb.minia.edu.eg/umisbuilt1/registration/pg_admin.aspx";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/postgraduate.aspx";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://mc.minia.edu.eg/research/";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://courses.minia.edu.eg/";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/deputy2.aspx";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://www.ekb.eg/ar/home;jsessionid=bR2WvNrM0I-rhF3PHy5ipvVr.undefined";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://thesis.minia.edu.eg/";
                startActivity(new Intent(High_stu.this,web.class));
            }
        });

    }
}
