package minia.chatapp.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import minia.chatapp.R;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextInputLayout emailTextInputLayout,passTextInputLayout;
    DatabaseReference mDatabaseReference;
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
    RadioGroup radioGroup;
    RadioButton radioUser,radioEmployee;
    String loginType = "user";
    Button btnRegister;
    TextView txtOr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this.setTitle("Login");

        radioGroup = findViewById(R.id.radioGroup);
        radioUser = findViewById(R.id.radioUser);
        radioEmployee = findViewById(R.id.radioEmployee);
        emailTextInputLayout=(TextInputLayout)findViewById(R.id.editText1);
        passTextInputLayout=(TextInputLayout)findViewById(R.id.editText2);

        sharedPreferences=getSharedPreferences("myDetails", Context.MODE_PRIVATE);

        progressDialog=new ProgressDialog(LoginActivity.this);


        btnRegister = findViewById(R.id.buttonRegister);
        txtOr = findViewById(R.id.txtOr);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.radioUser) {
                            loginType = "user";
                            btnRegister.setVisibility(View.VISIBLE);
                            txtOr.setVisibility(View.VISIBLE);
                        } else if (checkedId == R.id.radioEmployee) {
                            loginType = "employee";
                            btnRegister.setVisibility(View.GONE);
                            txtOr.setVisibility(View.GONE);
                        }
                    }

                });

//        Intent intent = getIntent();
//        loginType = intent.getStringExtra("type");
//        if (loginType.equals("employee")){
//            btnRegister.setVisibility(View.GONE);
//            txtOr.setVisibility(View.GONE);
//        }

    }

    //----SHOWING ALERT DIALOG FOR EXITING THE APP----
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
//        builder.setMessage("Really Exit ??");
//        builder.setTitle("Exit");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Ok",new MyListener());
//        builder.setNegativeButton("Cancel",null);
//        builder.show();
//
//    }


    public class MyListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    }

    public void buttonIsClicked(View view){

        switch(view.getId()){

            case R.id.buttonSign:

                String email=emailTextInputLayout.getEditText().getText().toString().trim();
                String password=passTextInputLayout.getEditText().getText().toString().trim();

                //---CHECKING IF EMAIL AND PASSWORD IS NOT EMPTY----
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please Fill all blocks", Toast.LENGTH_SHORT).show();
                    return ;
                }
                progressDialog.setTitle("Logging in");
                progressDialog.setMessage("Please wait while we are checking the credentials..");
                progressDialog.setCancelable(false);
                progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();


                if (loginType.equals("user")){
                    login(email,password, "users");
                }else {
                    login(email,password, "employees");
                }
                break;

            case R.id.buttonRegister:

                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void login(final String email, String password, final String typeName) {

        mauth=FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(typeName);

        //---SIGN IN FOR THE AUTHENTICATE EMAIL-----
        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                            final String userId=mauth.getCurrentUser().getUid();
                            String token_id= FirebaseInstanceId.getInstance().getToken();
                            Map addValue = new HashMap();
                            addValue.put("device_token",token_id);
                            addValue.put("online","true");



                            //---IF UPDATE IS SUCCESSFULL , THEN OPEN MAIN ACTIVITY---
                            mDatabaseReference.child(userId).updateChildren(addValue, new DatabaseReference.CompletionListener(){

                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                    if(databaseError==null){
                                        mDatabaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {


                                                final SharedPreferences.Editor editor=sharedPreferences.edit();
                                                editor.putString("name", dataSnapshot.child("name").toString());
                                                editor.putString("nationalId", dataSnapshot.child("nationalId").toString());
                                                editor.putString("phone", dataSnapshot.child("phone").toString());
                                                editor.putString("image", dataSnapshot.child("image").toString());
                                                editor.putString("type", loginType);
                                                editor.putString("email",email);
                                                editor.putString("id",userId);
                                                editor.putBoolean("isLogin", true);
                                                editor.apply();

                                                //---OPENING MAIN ACTIVITY---
                                                Log.e("Login : ","Logged in Successfully" );
                                                if (loginType.equals("user")){
                                                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                    if (sharedPreferences.contains("universityId") && sharedPreferences.contains("facultyId")) {
                                                        Intent intent=new Intent(LoginActivity.this, UserHomeActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }else{
                                                        Intent intent=new Intent(LoginActivity.this,SettingsActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }
                                                }else{
                                                    Intent intent=new Intent(LoginActivity.this, EmployeeHomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, databaseError.toString()  , Toast.LENGTH_SHORT).show();
                                        Log.e("Error is : ",databaseError.toString());

                                    }
                                }
                            });



                        }
                        else{
                            //---IF AUTHENTICATION IS WRONG----
                            Toast.makeText(LoginActivity.this, "Wrong Credentials" +
                                    "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
