package minia.chatapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import minia.chatapp.R;
import minia.chatapp.models.Room;

public class EmployeeProfileActivity extends AppCompatActivity {
    private DatabaseReference roomsDatabaseReference;
    TextView name, phone, department;
    Button startChat;
    String empId;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        department = findViewById(R.id.department);
        startChat = findViewById(R.id.btnStartChat);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        department.setText(intent.getStringExtra("department"));

        empId = intent.getStringExtra("id");

        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChatRooms();
            }
        });
    }

    ValueEventListener listener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            boolean isRoomExist = false;
            ArrayList<Room> rooms = new ArrayList();
            for (DataSnapshot dmo : dataSnapshot.getChildren()){
                Room room = dmo.getValue(Room.class);
                room.setKey(dmo.getKey());
                rooms.add(room);
            }

            SharedPreferences sharedPreferences = getSharedPreferences("myDetails", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("id", "1");
            String roomKey = "";
            for (Room room : rooms){
                if (room.getEmployeeId().equals(empId) && room.getUserId().equals(userId)){
                    isRoomExist = true;
                    roomKey = room.getKey();
                    break;
                }
            }

            if (!isRoomExist){
                Map roomMap=new HashMap();
                roomMap.put("userId",userId);
                roomMap.put("employeeId",empId);
                roomMap.put("createdAt", Calendar.getInstance().getTimeInMillis());

                roomsDatabaseReference.push().setValue(roomMap).addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EmployeeProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Intent intent = new Intent(EmployeeProfileActivity.this, UserChatActivity.class);
                intent.putExtra("roomKey", roomKey);
                intent.putExtra("userId", userId);
                intent.putExtra("empId", empId);
                startActivity(intent);
            }

            mProgressDialog.dismiss();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(EmployeeProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }
    };

    private void getChatRooms(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        roomsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("rooms");
        roomsDatabaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onStop() {
        if (roomsDatabaseReference != null)
           roomsDatabaseReference.removeEventListener(listener);
        super.onStop();
    }
}
