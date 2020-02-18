package minia.chatapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import minia.chatapp.models.University;

public class UniversitiesAdapter extends ArrayAdapter<University> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<University> universityList;

    public UniversitiesAdapter(Context context, int textViewResourceId,
                               List<University> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.universityList = values;
    }

    @Override
    public int getCount(){
        return universityList.size();
    }

    @Override
    public University getItem(int position){
        return universityList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(universityList.get(position).getName());
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(universityList.get(position).getName());

        return label;
    }
}