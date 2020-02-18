package minia.chatapp.aboutUnversity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import minia.chatapp.R;

public class Collage extends AppCompatActivity {

GridView gridView;
ArrayList<Item> items;
MyAdapter myAdapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_surveyrecycle);
    getItem();
    gridView=findViewById(R.id.grid);
    myAdapter=new MyAdapter(this,items);
    gridView.setAdapter(myAdapter);
    myAdapter.setOncatagoyItem(new MyAdapter.OncatagoyItem() {
        @Override
        public void oncatagoyitem(Item item, long postion) {
            if(item.getId()==0)
            {
                web.ul_name="https://www.minia.edu.eg/fci/";
                startActivity(new Intent(Collage.this,web.class));

            }

            else if(item.getId()==1)
            {
                web.ul_name="https://www.minia.edu.eg/med/";
                startActivity(new Intent(Collage.this,web.class));
            }


            else if(item.getId()==2)
            {
                web.ul_name="https://www.minia.edu.eg/eng/";
                startActivity(new Intent(Collage.this,web.class));
            }

            else if(item.getId()==3)
            {

                web.ul_name="https://www.minia.edu.eg/tour/";
                startActivity(new Intent(Collage.this,web.class));
            }
            else if(item.getId()==4)
            {
                web.ul_name="https://www.minia.edu.eg/pharm/";
                startActivity(new Intent(Collage.this,web.class));

            }
            else if(item.getId()==5)
            {
                web.ul_name="https://www.minia.edu.eg/farts/";
                startActivity(new Intent(Collage.this,web.class));
            }

            else if(item.getId()==6)
            {
                web.ul_name="https://www.minia.edu.eg/dent/";
                startActivity(new Intent(Collage.this,web.class));
            }

            else if(item.getId()==7)
            {

                web.ul_name="https://www.minia.edu.eg/alsun/";
                startActivity(new Intent(Collage.this,web.class));
            }
            else if(item.getId()==8)
            {


                web.ul_name="https://www.minia.edu.eg/vet/";
                startActivity(new Intent(Collage.this,web.class));


            }
            else if(item.getId()==9)
            {


                web.ul_name="https://www.minia.edu.eg/edu/";
                startActivity(new Intent(Collage.this,web.class));


            }
            else if(item.getId()==10)
            {


                web.ul_name="https://www.minia.edu.eg/nurse/";
                startActivity(new Intent(Collage.this,web.class));


            }
            else if(item.getId()==11)
            {


                web.ul_name="https://www.minia.edu.eg/artedu/";
                startActivity(new Intent(Collage.this,web.class));


            } else if(item.getId()==12)
            {


                web.ul_name="https://www.minia.edu.eg/sci/";
                startActivity(new Intent(Collage.this,web.class));


            }
            else if(item.getId()==13)
            {


                web.ul_name="https://www.minia.edu.eg/phedu/";
                startActivity(new Intent(Collage.this,web.class));


            }
            else if(item.getId()==14)
            {


                web.ul_name="https://www.minia.edu.eg/law/";
                startActivity(new Intent(Collage.this,web.class));


            }
            else if(item.getId()==15)
            {


                web.ul_name="https://www.minia.edu.eg/arts/";
                startActivity(new Intent(Collage.this,web.class));


            } else if(item.getId()==16)
            {


                web.ul_name="https://www.minia.edu.eg/dar/";
                startActivity(new Intent(Collage.this,web.class));


            } else if(item.getId()==17)
            {


                web.ul_name="https://www.minia.edu.eg/kind/";
                startActivity(new Intent(Collage.this,web.class));


            } else if(item.getId()==18)
            {


                web.ul_name="https://www.minia.edu.eg/agr/";
                startActivity(new Intent(Collage.this,web.class));


            } else if(item.getId()==19)
            {


                web.ul_name="https://www.minia.edu.eg/spedu/";
                startActivity(new Intent(Collage.this,web.class));


            }


        }
    });
}
private void getItem() {
    items = new ArrayList<>();


    items.add(new Item("كلية الحاسبات والمعلومات",  R.drawable.fci,0));
    items.add(new Item("كلية الطب      ", R.drawable.teb,1));

    items.add(new Item("كلية الهندسة",  R.drawable.eng,2));
    items.add(new Item("كليةالسياحةوالفنادق ", R.drawable.syiha,3));
    items.add(new Item(" كلية الصيدلة ", R.drawable.ssidla,4));
    items.add(new Item(" كلية الفنون الجميلة ", R.drawable.fnono,5));
    items.add(new Item(" كلية الأسنان  ", R.drawable.asnaan,6));
    items.add(new Item("  كلية الألسن ", R.drawable.alson,7));
    items.add(new Item(" كلية الطب البيطري", R.drawable.betary,8));
    items.add(new Item("كلية التربية ", R.drawable.tarbia,9));
    /////////////////////////
    items.add(new Item("كلية التمريض ", R.drawable.temrede,10));
    items.add(new Item(" كلية التربية الفنية", R.drawable.finyyya,11));
    items.add(new Item("كلية العلوم ", R.drawable.olommaa,12));
    items.add(new Item("كلية التربية الرياضية ", R.drawable.reydia,13));
    items.add(new Item("كلية الحقوق ", R.drawable.law,14));
    items.add(new Item("كلية الاداب ", R.drawable.adab,15));
    items.add(new Item(" كلية دار العلوم", R.drawable.darolmmaa,16));
    items.add(new Item(" كلية التربية للطفوله المكبرة", R.drawable.tefola,17));
    items.add(new Item(" كلية الزراعة ", R.drawable.zeria,18));
    items.add(new Item("كلية التربية النوعية ", R.drawable.nawiay,19));
  //  items.add(new Item(" ", R.drawable.z,20));

}

}
