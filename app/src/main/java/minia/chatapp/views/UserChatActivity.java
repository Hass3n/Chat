package minia.chatapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import minia.chatapp.R;
import minia.chatapp.adapters.MessageAdapter;
import minia.chatapp.models.Users;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
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
    ImageView imgSend, imgUpload;
    DatabaseReference messagesDatabaseReference;
    DatabaseReference usersDatabaseReference;
    DatabaseReference employeesDatabaseReference;
    DatabaseReference roomDatabaseReference;
    String roomKey;
    String userId;
    String empId;
    String empToken;
    int Image_Request_Code = 7;
    private static StorageReference storageReference;
    ArrayList<Message> messageList;
    private static final int GALLERY_PICK=1;
    Uri FilePathUri;
    String userName;
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        storageReference = FirebaseStorage.getInstance().getReference();

        messagesRecycler = findViewById(R.id.messagesRecycler);
        editMessage = findViewById(R.id.editMessage);
        imgSend = findViewById(R.id.imgSend);
        imgUpload = findViewById(R.id.chatAddimagee);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( UserChatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( UserChatActivity.this, new String[] {  Manifest.permission.READ_EXTERNAL_STORAGE  },
                            0 );
                    return  ;
                }

              openGallery();
            }
        });

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

    private void openGallery(){
        // Creating intent.
        Intent intent = new Intent();

        // Setting intent type as image to select image from phone storage.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), Image_Request_Code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0){
            openGallery();
        }
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

    private void sendImage(final String url){
        Map messageMap=new HashMap();
        final Long createdAt = Calendar.getInstance().getTimeInMillis();
        messageMap.put("roomId",roomKey);
        messageMap.put("imageUrl", url);
        messageMap.put("content","photo");
        messageMap.put("type","fromUserToEmployee");
        messageMap.put("createdAt", createdAt);

        messagesDatabaseReference.push().setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserChatActivity.this, "message sent successfully", Toast.LENGTH_LONG).show();
                    if (empToken != null){
                        sendNotification("photo");
                    }
                    roomDatabaseReference = FirebaseDatabase.getInstance().getReference().child("rooms");
                    roomDatabaseReference.keepSynced(true);

                    Map updateRoomMap = new HashMap();
                    updateRoomMap.put("lastSentMessage","photo");
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
                userName = user.getName();

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
                                MessageAdapter adapter = new MessageAdapter(UserChatActivity.this, messageList);
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
        data.put("senderName", userName);
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

    //---THIS FUNCTION IS CALLED WHEN SYSTEM ACTIVITY IS CALLED---


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // FilePathUri = data.getData();

            FilePathUri = data.getData();


            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                UploadImageFileToFirebaseStorage();
                // Setting up bitmap selected image into ImageView.
                //  image.setImageBitmap(bitmap);


            } catch (IOException e) {

                e.printStackTrace();
            }
        }




    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.

        if (FilePathUri != null) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mProgressDialog.dismiss();
                            // Showing toast message after done uploading.
                           // Toast.makeText(getApplicationContext(), "تم رفع الصور بنجاح ", Toast.LENGTH_LONG).show();
                            sendImage(taskSnapshot.getDownloadUrl().toString());

                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                              mProgressDialog.dismiss();
                            // Showing exception erro message.
                            Toast.makeText(UserChatActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


        }

    }




}
