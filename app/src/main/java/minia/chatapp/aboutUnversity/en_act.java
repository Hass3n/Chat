package minia.chatapp.aboutUnversity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import minia.chatapp.R;

public class en_act extends AppCompatActivity {

    Button en1,en2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_act);
        en1=findViewById(R.id.en_se1);
        en2=findViewById(R.id.en_se2);
        en1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/deputy3.aspx";
                startActivity(new Intent(en_act.this,web.class));
            }
        });
        en2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name=" http://hotel.minia.edu.eg/";
                startActivity(new Intent(en_act.this,web.class));
            }
        });

    }
}
