package umn.ac.tugasmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailUser extends AppCompatActivity {

    TextView namalengkap, tvdisplay, tvangkatan, tvnim, tvstatus, tvpekerjaan,tvnohp,tvdomisili;
    ImageView ivpp, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        final Intent i=getIntent();
        namalengkap = findViewById(R.id.fullname);
        tvdisplay = findViewById(R.id.username);
        tvangkatan = findViewById(R.id.angkatan);
        ivpp = findViewById(R.id.pp);
        tvnim = findViewById(R.id.nim);
        tvstatus = findViewById(R.id.Status);
        tvpekerjaan = findViewById(R.id.Pekerjaan);
        tvnohp = findViewById(R.id.nohp);
        tvdomisili = findViewById(R.id.domisili);
        back = findViewById(R.id.back);

        String nama=i.getStringExtra("nama");
        String displayname=i.getStringExtra("displayname");
        String angkatan=i.getStringExtra("angkatan");
        String pp=i.getStringExtra("pp");
        String nim=i.getStringExtra("nim");
        String status=i.getStringExtra("status");
        String pekerjaan=i.getStringExtra("pekerjaan");
        String nohp=i.getStringExtra("nohp");
        String domisili=i.getStringExtra("domisili");

        tvdisplay.setText(displayname);
        namalengkap.setText(nama);
        tvangkatan.setText(angkatan);
        tvnim.setText(nim);
        tvstatus.setText(status);
        tvpekerjaan.setText(pekerjaan);
        tvnohp.setText(nohp);
        tvdomisili.setText(domisili);

        if(pp.equals("default")){
            ivpp.setImageResource(R.drawable.blank);
        }
        else{
            Glide.with(DetailUser.this).load(pp).into(ivpp);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(DetailUser.this,
                        Home.class);
                startActivityForResult(addIntent,1);
            }
        });

    }
}
