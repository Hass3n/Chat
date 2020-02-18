package minia.chatapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import minia.chatapp.R;
import minia.chatapp.adapters.MessageAdapter;
import minia.chatapp.models.Users;

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

import minia.chatapp.models.Employee;
import minia.chatapp.models.Message;
import minia.chatapp.retrofit.RetrofitWebService;
import minia.chatapp.retrofit.WebServiceModels.DataMessage;
import minia.chatapp.retrofit.response.FCMResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatActivity extends AppCompatActivity {
    RecyclerView messagesRecycler;
    EditText editMessage;
    ImageView imgSend;
    DatabaseReference messagesDatabaseReference;
    DatabaseReference usersDatabaseReference;
    DatabaseReference employeesDatabaseReference;
    DatabaseReference roomDatabaseReference;
    String roomKey;
    String userId;
    String empId;
    String empToken;
    ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        messagesRecycler = findViewById(R.id.messagesRecycler);
        editMessage = findViewById(R.id.editMessage);
        imgSend = findViewById(R.id.imgSend);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = editMessage.getText().toString();
                if (!content.isEmpty()){
                    sendMessage(content);
                    editMessage.setText("");
                }
            }
        });

        Intent intent = getIntent();
        roomKey = intent.getStringExtra("roomKey");
        userId = intent.getStringExtra("userId");
        empId = intent.getStringExtra("empId");

        loadMessages();
    }

    private void sendMessage(final String content){
        Map messageMap=new HashMap();
        final Long createdAt = Calendar.getInstance().getTimeInMillis();
        messageMap.put("roomId",roomKey);
        messageMap.put("content", content);
        messageMap.put("type","fromUserToEmployee");
        messageMap.put("createdAt", createdAt);

        messagesDatabaseReference.push().setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserChatActivity.this, "message sent successfully", Toast.LENGTH_LONG).show();
                    if (empToken != null){
                        sendNotification(content);
                    }
                    roomDatabaseReference = FirebaseDatabase.getInstance().getReference().child("rooms");
                    roomDatabaseReference.keepSynced(true);

                    Map updateRoomMap = new HashMap();
                    updateRoomMap.put("lastSentMessage",content);
                    updateRoomMap.put("lastMessageCreatedTime",createdAt);
                    roomDatabaseReference.child(roomKey).updateChildren(updateRoomMap);
                }
            }
        });
    }


    private void loadMessages(){
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        usersDatabaseReference.keepSynced(true);
        usersDatabaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Users user = dataSnapshot.getValue(Users.class);
                user.setKey(dataSnapshot.getKey());

                employeesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("employees");
                employeesDatabaseReference.keepSynced(true);
                employeesDatabaseReference.child(empId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Employee employee = dataSnapshot.getValue(Employee.class);
                        employee.setKey(dataSnapshot.getKey());
                        empToken = employee.getDevice_token();


                        messageList = new ArrayList<>();
                        messagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
                        messagesDatabaseReference.keepSynced(true);
                        messagesDatabaseReference.orderByChild("roomId").equalTo(roomKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                messageList.clear();
                                for (DataSnapshot dmo : dataSnapshot.getChildren())
                                {
                                    Message message = dmo.getValue(Message.class);
                                    message.setKey(dmo.getKey());
                                    message.setUser(user);
                                    message.setEmployee(employee);
                                    messageList.add(message);
                                }

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserChatActivity.this);
                                messagesRecycler.setLayoutManager(linearLayoutManager);
                                MessageAdapter adapter = new MessageAdapter(messageList);
                                messagesRecycler.setAdapter(adapter);
                                messagesRecycler.scrollToPosition(messageList.size()-1);

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
                        mProgressDialog.dismiss();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void sendNotification(String content){
        Map<String,String> data=new HashMap<>();
        data.put("roomKey", roomKey);
        data.put("userId", userId);
        data.put("empId", empId);
        data.put("content", content);
        data.put("type", "fromUserToEmployee");
        DataMessage dataMessage=new DataMessage(empToken,data);

        RetrofitWebService.getFcmService().sendMessage(dataMessage).enqueue(new Callback<FCMResponse>() {
            @Override
            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                if (response.body().success == 1)
                    Toast.makeText(UserChatActivity.this, "Request Sent!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(UserChatActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FCMResponse> call, Throwable t) {
                Log.e("Error_in_request", t.getMessage());
            }
        });
    }
}
