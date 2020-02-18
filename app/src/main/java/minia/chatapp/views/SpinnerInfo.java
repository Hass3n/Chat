package minia.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import minia.chatapp.R;


public class SpinnerInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String universitySellectedItem, facultySellectedItem;

    CardView cardView;

    Spinner universitySpinner,facultySpinner;
    String[]university={"University","cairo","Alexandria","Banha","Assuite","Minya","Sohag","Ain Shams","Helwan"+
            "","Qina","Banisweef","Aswan"};

    String[]faculty={"faculty","midicine","Education","Law","Computer and Information"+
            "","Engineering","Specific Education","Nursing","Arts"+
            "","Science"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinne_main);
        universitySpinner=findViewById(R.id.id_university_spinner);
        facultySpinner=findViewById(R.id.id_Faculty_spinner);

        cardView=findViewById(R.id.card_view);
        cardView.setRadius(5);

        ArrayAdapter<String> universityAdapter=new ArrayAdapter<>(this,R.layout.spinner_item_shape,university);
        universitySpinner.setAdapter(universityAdapter);

        ArrayAdapter<String> facultyAdapter=new ArrayAdapter<>(this,R.layout.spinner_item_shape,faculty);
        facultySpinner.setAdapter(facultyAdapter);

        universitySpinner.setOnItemSelectedListener(this);
        facultySpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==universitySpinner.getId())
        {
            if(position!=0)
            {
                universitySellectedItem=university[position];
                Toast.makeText(SpinnerInfo.this,universitySellectedItem+" university",Toast.LENGTH_LONG).show();

            }//if end

        }//if end
        else if(parent.getId()==facultySpinner.getId())
        {
            if(position!=0)
            {
                facultySellectedItem=faculty[position];
                Toast.makeText(SpinnerInfo.this,facultySellectedItem+" faculty",Toast.LENGTH_LONG).show();

            }//if end

        }//if end
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
