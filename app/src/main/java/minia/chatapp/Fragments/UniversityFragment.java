package minia.chatapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import minia.chatapp.R;
import minia.chatapp.aboutUnversity.Collage;
import minia.chatapp.aboutUnversity.High_stu;
import minia.chatapp.aboutUnversity.Item;
import minia.chatapp.aboutUnversity.MyAdapter;
import minia.chatapp.aboutUnversity.Tec_su;
import minia.chatapp.aboutUnversity.UnitandCenter;
import minia.chatapp.aboutUnversity.Universty;
import minia.chatapp.aboutUnversity.en_act;
import minia.chatapp.aboutUnversity.student_su;
import minia.chatapp.aboutUnversity.web;


public class UniversityFragment extends Fragment {

   View view;
    GridView gridView;
    ArrayList<Item> items;
    MyAdapter myAdapter;

    public UniversityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      view=  inflater.inflate(R.layout.fragment_university, container, false);


        getItem();
        gridView=view.findViewById(R.id.grid);
        myAdapter=new MyAdapter(getContext(),items);
        gridView.setAdapter(myAdapter);
        myAdapter.setOncatagoyItem(new MyAdapter.OncatagoyItem() {
            @Override
            public void oncatagoyitem(Item item, long postion) {
                if(item.getId()==0)
                {
                    web.ul_name="https://www.minia.edu.eg/myfawry/";
                    startActivity(new Intent(getContext(),web.class));

                }

                else if(item.getId()==1)
                {
                    startActivity(new Intent(getContext(), student_su.class));
                }


                else if(item.getId()==2)
                {
                    startActivity(new Intent(getContext(), Universty.class));
                }

                else if(item.getId()==3)
                {

                    web.ul_name="https://www.minia.edu.eg/Minia/media2.aspx?c_id=1";
                    startActivity(new Intent(getContext(),web.class));
                }
                else if(item.getId()==4)
                {

                    startActivity(new Intent(getContext(), Tec_su.class));
                }
                else if(item.getId()==5)
                {

                    startActivity(new Intent(getContext(), en_act.class));
                }

                else if(item.getId()==6)
                {
                    web.ul_name="https://www.minia.edu.eg/Minia/complian2.aspx";
                    startActivity(new Intent(getContext(),web.class));
                }

                else if(item.getId()==7)
                {

                    startActivity(new Intent(getContext(), UnitandCenter.class));
                }
                else if(item.getId()==8)
                {

                    startActivity(new Intent(getContext(), High_stu.class));



                }
                else if(item.getId()==9)
                {

                    startActivity(new Intent(getContext(), Collage.class));



                }
            }
        });







        return view;


    }
    private void getItem() {
        items = new ArrayList<>();
        items.add(new Item("التحصيل الالكتروني", R.drawable.o,0));

        items.add(new Item("خدمات طلابية",  R.drawable.w,1));
        items.add(new Item("  عن الجامعة    ", R.drawable.uv,2));
        items.add(new Item("اخبار الجامعة",  R.drawable.www,3));
        items.add(new Item(" هيئة التدريس", R.drawable.a,4));
        items.add(new Item("قطاع شئون البيئة ", R.drawable.uv,5));
        items.add(new Item("  شكاوى ومقترحات  ", R.drawable.t,6));
        items.add(new Item("  مراكز ووحدات  ", R.drawable.center,7));
        items.add(new Item("الدراسات العليا", R.drawable.z,8));
        items.add(new Item(" الكليات", R.drawable.koko,9));

    }



}
