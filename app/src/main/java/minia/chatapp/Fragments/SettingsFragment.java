package minia.chatapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import minia.chatapp.R;
import minia.chatapp.views.UserHomeActivity;
import minia.chatapp.adapters.FacultiesAdapter;
import minia.chatapp.adapters.UniversitiesAdapter;
import minia.chatapp.models.Faculty;
import minia.chatapp.models.University;


public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private DatabaseReference universityReference;
    private DatabaseReference facultyReference;
    List<University> universityList = new ArrayList<>();
    List<Faculty> facultyList = new ArrayList<>();
    long universitySellectedItem = 0, facultySellectedItem = 0;

    CardView cardView;

    Spinner universitySpinner,facultySpinner;
    Button doneBtn;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        universitySpinner= view.findViewById(R.id.id_university_spinner);
        facultySpinner= view.findViewById(R.id.id_Faculty_spinner);
        doneBtn = view.findViewById(R.id.btnDone);
        cardView= view.findViewById(R.id.card_view);
        cardView.setRadius(5);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUniversities();

        universitySpinner.setOnItemSelectedListener(this);
        facultySpinner.setOnItemSelectedListener(this);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (universitySellectedItem != 0 && facultySellectedItem != 0){

                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("myDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("universityId", universitySellectedItem);
                    editor.putLong("facultyId", facultySellectedItem);
                    editor.apply();

                    Intent intent = new Intent(getContext(), UserHomeActivity.class);
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

                ArrayAdapter<Faculty> facultyArrayAdapter=new FacultiesAdapter(getContext(), R.layout.spinner_item_shape,facultyList);
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
        mProgressDialog = new ProgressDialog(getContext());
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

                ArrayAdapter<University> universityAdapter=new UniversitiesAdapter(getContext(), R.layout.spinner_item_shape,universityList);
                universitySpinner.setAdapter(universityAdapter);

                universitySpinner.setSelection(0);

                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), databaseError.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void loadFaculties(final long universityId){
        final ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(getContext());
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

                ArrayAdapter<Faculty> facultyArrayAdapter=new FacultiesAdapter(getContext(), R.layout.spinner_item_shape,facultyList);
                facultySpinner.setAdapter(facultyArrayAdapter);
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), databaseError.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });
    }
}
