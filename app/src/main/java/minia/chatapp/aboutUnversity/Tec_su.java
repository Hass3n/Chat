   package minia.chatapp.aboutUnversity;


   import android.content.Intent;
   import android.os.Bundle;
   import android.view.View;
   import android.widget.Button;

   import androidx.appcompat.app.AppCompatActivity;

   import minia.chatapp.R;

   public class Tec_su extends AppCompatActivity {

     Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tec_su);
        bt1=findViewById(R.id.tec_se1);
        bt2=findViewById(R.id.tec_se2);
        bt3=findViewById(R.id.tec_se3);
        bt4=findViewById(R.id.tec_se4);
        bt5=findViewById(R.id.tec_se5);
        bt6=findViewById(R.id.tec_se6);
        bt7=findViewById(R.id.tec_se7);
        bt8=findViewById(R.id.tec_se8);
        bt9=findViewById(R.id.tec_se9);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://scholar.google.com/citations?view_op=search_authors&hl=en&mauthors=minia.edu.eg&btnG=&fbclid=IwAR0uKmU3Gheaq7vpBK-CoSWsKASLhLv-wULHxK4dW";
                startActivity(new Intent(Tec_su.this,web.class));

            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.researchgate.net/institution/Minia_University/publications";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://staffportal.minia.edu.eg/";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/e-mail.aspx";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://departments.minia.edu.eg/";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://misdb.minia.edu.eg/miniaportal/StaffDefault.aspx";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/qeustions.";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="http://mc.minia.edu.eg/fldc/";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });
        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.ul_name="https://www.minia.edu.eg/Minia/staffemail2.aspx";
                startActivity(new Intent(Tec_su.this,web.class));
            }
        });
    }
}
