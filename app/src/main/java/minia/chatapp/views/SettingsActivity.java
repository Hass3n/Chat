package minia.chatapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import minia.chatapp.R;
import minia.chatapp.adapters.FacultiesAdapter;
import minia.chatapp.adapters.UniversitiesAdapter;
import minia.chatapp.models.Faculty;
import minia.chatapp.models.University;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference universityReference;
    private DatabaseReference facultyReference;
    List<University> universityList = new ArrayList<>();
    List<Faculty> facultyList = new ArrayList<>();
    long universitySellectedItem = 0, facultySellectedItem = 0;

    CardView cardView;

    Spinner universitySpinner,facultySpinner;
    Button doneBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        universitySpinner=findViewById(R.id.id_university_spinner);
        facultySpinner=findViewById(R.id.id_Faculty_spinner);
        doneBtn = findViewById(R.id.btnDone);

        cardView=findViewById(R.id.card_view);
        cardView.setRadius(5);

        loadUniversities();

        universitySpinner.setOnItemSelectedListener(this);
        facultySpinner.setOnItemSelectedListener(this);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (universitySellectedItem != 0 && facultySellectedItem != 0){

                    SharedPreferences sharedPreferences = getSharedPreferences("myDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("universityId", universitySellectedItem);
                    editor.putLong("facultyId", facultySellectedItem);
                    editor.apply();

                    Intent intent = new Intent(SettingsActivity.this, UserHomeActivity.class);
                    intent.putExtra("universityId", universitySellectedItem);
                    intent.putExtra("facultyId", facultySellectedItem);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==universitySpinner.getId())
        {
            if(position!=0)
            {
                universitySellectedItem = universityList.get(position).getId();
                facultySellectedItem = 0;
                loadFaculties(universitySellectedItem);
            }else {
                facultyList.clear();
                Faculty faculty = new Faculty();
                faculty.setId(0);
                faculty.setName(getString(R.string.select_faculty));
                facultyList.add(faculty);

                ArrayAdapter<Faculty> facultyArrayAdapter=new FacultiesAdapter(SettingsActivity.this, R.layout.spinner_item_shape,facultyList);
                facultySpinner.setAdapter(facultyArrayAdapter);
            }//if end//if end

        }//if end
        else if(parent.getId()==facultySpinner.getId())
        {
            if(position!=0)
            {
                facultySellectedItem=facultyList.get(position).getId();
            }

        }//if end
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void loadUniversities(){
        final ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        universityReference = FirebaseDatabase.getInstance().getReference("universities");
        universityReference.keepSynced(true);

        universityReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                universityList.clear();
                University university = new University();
                university.setId(0);
                university.setName(getString(R.string.select_university));
                universityList.add(university);

                for (DataSnapshot dmo : dataSnapshot.getChildren()) {
                    university = dmo.getValue(University.class);
                    universityList.add(university);
                }

                ArrayAdapter<University> universityAdapter=new UniversitiesAdapter(SettingsActivity.this, R.layout.spinner_item_shape,universityList);
                universitySpinner.setAdapter(universityAdapter);

                universitySpinner.setSelection(0);

                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
                Toast.makeText(SettingsActivity.this, databaseError.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void loadFaculties(final long universityId){
        final ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        facultyReference = FirebaseDatabase.getInstance().getReference("faculties");
        facultyReference.keepSynced(true);

        facultyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                facultyList.clear();
                Faculty faculty = new Faculty();
                faculty.setId(0);
                faculty.setName(getString(R.string.select_faculty));
                facultyList.add(faculty);
                for (DataSnapshot dmo : dataSnapshot.getChildren()) {

                    faculty = dmo.getValue(Faculty.class);
                    if(faculty.getUniversity_id() == universityId){
                        facultyList.add(faculty);
                    }
                }

                ArrayAdapter<Faculty> facultyArrayAdapter=new FacultiesAdapter(SettingsActivity.this, R.layout.spinner_item_shape,facultyList);
                facultySpinner.setAdapter(facultyArrayAdapter);
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
                Toast.makeText(SettingsActivity.this, databaseError.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });
    }
}
