package minia.chatapp.views;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import minia.chatapp.R;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    TextInputLayout etdisplayname,etemail,etpassword,national_id,address,phone,type,U_name,F_name;
    Button buttonsubmit;
    ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //this.setTitle("Register");

        etdisplayname=(TextInputLayout)findViewById(R.id.editText3);
        etemail=(TextInputLayout)findViewById(R.id.editText4);
        etpassword=(TextInputLayout)findViewById(R.id.editText5);
        national_id=(TextInputLayout)findViewById(R.id.editText6);
        address=(TextInputLayout)findViewById(R.id.editText7);
        phone=(TextInputLayout)findViewById(R.id.editText8);

        buttonsubmit=(Button)findViewById(R.id.button3);



        progressDialog=new ProgressDialog(RegisterActivity.this);

        mauth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("users");
    }

    //-----REGISTER BUTTON IS PRESSED---
    public void buttonIsClicked(View view){

        if(view.getId()==R.id.button3){

            String displayname=etdisplayname.getEditText().getText().toString().trim();
            String email=etemail.getEditText().getText().toString().trim();
            String password=etpassword.getEditText().getText().toString().trim();
            String nationalId=national_id.getEditText().getText().toString().trim();
            String Location=address.getEditText().getText().toString().trim();
            String phone_num=phone.getEditText().getText().toString().trim();

            //----CHECKING THE EMPTINESS OF THE EDITTEXT-----
            if(displayname.equals("")){
                Toast.makeText(RegisterActivity.this, "Please Fill the name", Toast.LENGTH_SHORT).show();
                return;
            }

            if(email.equals("")){
                Toast.makeText(RegisterActivity.this, "Please Fill the email", Toast.LENGTH_SHORT).show();
                return ;
            }


           /* if(Type.equals("")){
                Toast.makeText(RegisterActivity.this, "Please Fill the type", Toast.LENGTH_SHORT).show();
                return ;
            }*/

            if(password.length()<6){
                Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Location.equals("")){
                Toast.makeText(RegisterActivity.this, "Please Fill the Adress", Toast.LENGTH_SHORT).show();
                return;
            }
            if(nationalId.length()<14){
                Toast.makeText(RegisterActivity.this, "national id not complete ", Toast.LENGTH_SHORT).show();
                return;
            }
            if(phone_num.length()<11){
                Toast.makeText(RegisterActivity.this, "phone number should be 11 digit ", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setTitle("Registering User");
            progressDialog.setMessage("Please wait while we are creating your account... ");
            progressDialog.setCancelable(false);
            progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            register_user(displayname,email,password,Location,nationalId,phone_num);
        }
    }


    //-----REGISTERING THE NEW USER------
    private void register_user(final String displayname, final String email, String password, final String Location, final String national_id, final String phone_num) {

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //------IF USER IS SUCCESSFULLY REGISTERED-----
                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    final String uid=current_user.getUid();
                    String token_id = FirebaseInstanceId.getInstance().getToken();
                    Map userMap=new HashMap();
                    userMap.put("device_token",token_id);
                    userMap.put("name",displayname);
                    userMap.put("address",Location);
                    userMap.put("nationalId",national_id);
                    userMap.put("phone",phone_num);
                    userMap.put("email",email);
                    userMap.put("status","Hello Fci");
                    userMap.put("image","default");
                    userMap.put("online","true");


                    mDatabase.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "New User is created", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                intent.putExtra("type", "user");
                                //----REMOVING THE LOGIN ACTIVITY FROM THE QUEUE----
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{

                                Toast.makeText(RegisterActivity.this, "YOUR NAME IS NOT REGISTERED... MAKE NEW ACCOUNT-- ", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                }
                //---ERROR IN ACCOUNT CREATING OF NEW USER---
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "ERROR REGISTERING USER....", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
