package minia.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import minia.chatapp.R;

public class TypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        Button btnUser = findViewById(R.id.btnUser);
        Button btnEmployee = findViewById(R.id.btnEmployee);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, LoginActivity.class);
                intent.putExtra("type", "user");
                startActivity(intent);
            }
        });

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, LoginActivity.class);
                intent.putExtra("type", "employee");
                startActivity(intent);
            }
        });
    }
}
