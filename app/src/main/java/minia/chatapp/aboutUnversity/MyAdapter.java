package minia.chatapp.aboutUnversity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import minia.chatapp.R;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;
    OncatagoyItem oncatagoyItem;

    public void setOncatagoyItem(OncatagoyItem oncatagoyItem) {
        this.oncatagoyItem = oncatagoyItem;
    }

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }
    View paent;

    @Override

    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.items, null);
        TextView textView=view.findViewById(R.id.tv_name);
        ImageView imageView=view.findViewById(R.id.imgview);
        paent=view;
        final Item currentitem= (Item) getItem(i);
        textView.setText(currentitem.getName());
        imageView.setImageResource(currentitem.getImage());
        if (oncatagoyItem!=null)
        {

            paent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    oncatagoyItem.oncatagoyitem(currentitem,getItemId(i));
                }
            });
        }



        return view;
    }

    public interface OncatagoyItem
    {
        public void oncatagoyitem(Item item, long postion);


    }
}
