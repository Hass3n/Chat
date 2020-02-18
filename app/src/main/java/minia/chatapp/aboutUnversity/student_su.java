package minia.chatapp.aboutUnversity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import minia.chatapp.R;


public class student_su extends AppCompatActivity {

    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12,bt13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_su);
        bt1=findViewById(R.id.std_se1);
        bt2=findViewById(R.id.std_se2);
        bt3=findViewById(R.id.std_se3);
        bt4=findViewById(R.id.std_se4);
        bt5=findViewById(R.id.std_se5);
        bt6=findViewById(R.id.std_se6);

        bt8=findViewById(R.id.std_se8);
        bt9=findViewById(R.id.std_se9);
        bt10=findViewById(R.id.std_se10);
        bt11=findViewById(R.id.std_se11);
        bt12=findViewById(R.id.std_se12);
        bt13=findViewById(R.id.std_se13);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name=" https://www.minia.edu.eg/Minia/results.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/tables.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/Astudents.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/Astudents.aspx#";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/program/";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://courses.minia.edu.eg/";
                startActivity(new Intent(student_su.this,web.class));
            }
        });




        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://misdb.minia.edu.eg/miniaportal/GraduateDefault.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/mesak.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://courses.minia.edu.eg/Interface/Exams";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://alzahraa.mans.edu.eg/studentApplications";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/certeficate.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });

        bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name= "https://www.minia.edu.eg/Minia/studload.aspx";
                startActivity(new Intent(student_su.this,web.class));
            }
        });
    }




}
