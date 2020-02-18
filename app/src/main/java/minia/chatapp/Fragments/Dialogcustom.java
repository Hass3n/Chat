package minia.chatapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import minia.chatapp.views.Main2Activity;
import minia.chatapp.R;


public class Dialogcustom extends DialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    String universitySellectedItem, facultySellectedItem;
    SharedPreferences sharedPreferences, sharedPreferenc;
    Spinner universitySpinner,facultySpinner;
    Button btn;
    String[]university={"University","Cairo","Alexandria","Banha","Minya"
           };

    String[]faculty={"faculty","Midicine","Law","Computer and Information",

            "Science"};
    CardView cardView;
    View view;

    public Dialogcustom()
    {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.custom,container,false);
        universitySpinner=view.findViewById(R.id.id_university_spinner);
        facultySpinner=view.findViewById(R.id.id_Faculty_spinner);

        cardView=view.findViewById(R.id.card_view);
        btn=view.findViewById(R.id.btn_send);

        cardView.setRadius(5);

        ArrayAdapter<String> universityAdapter=new ArrayAdapter<>(getContext(),R.layout.spinner_item_shape,university);

        universitySpinner.setAdapter(universityAdapter);

        ArrayAdapter<String> facultyAdapter=new ArrayAdapter<>(getContext(),R.layout.spinner_item_shape,faculty);
        facultySpinner.setAdapter(facultyAdapter);

        universitySpinner.setOnItemSelectedListener(this);
        facultySpinner.setOnItemSelectedListener(this);
        sharedPreferences=getActivity().getSharedPreferences("Customuniversity", Context.MODE_PRIVATE);
       // sharedPreferenc=getActivity().getSharedPreferences("faculty", Context.MODE_PRIVATE);
        btn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==universitySpinner.getId())
        {
            if(position!=0)
            {
                universitySellectedItem=university[position];



                Toast.makeText(getContext(),universitySellectedItem+" university",Toast.LENGTH_LONG).show();

            }

        }
        else if(parent.getId()==facultySpinner.getId())
        {
            if(position!=0)
            {
                facultySellectedItem=faculty[position];



                Toast.makeText(getContext(),facultySellectedItem+" faculty",Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {




    }


    @Override
    public void onClick(View v) {



        if(v.getId()==R.id.btn_send)
        {

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("universitySellectedItem",universitySellectedItem+"");
            editor.putString( "facultySellectedItem", facultySellectedItem+"");
            editor.commit();
            Intent in=new Intent(getContext(), Main2Activity.class);
            startActivity(in);
            dismiss();

        }


    }
}
