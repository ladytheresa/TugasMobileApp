package umn.ac.tugasmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    ImageView back,pp;
    TextView username;
    EditText fullname,nim,angkatan,Status,Pekerjaan,nohp,domisili;
    FirebaseUser firebaseUser;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userRef= FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        back = findViewById(R.id.back);
        username = findViewById(R.id.username);
        pp = findViewById(R.id.pp);
        fullname = findViewById(R.id.fullname);
        nim = findViewById(R.id.nim);
        angkatan = findViewById(R.id.angkatan);
        Status = findViewById(R.id.Status);
        Pekerjaan = findViewById(R.id.Pekerjaan);
        nohp = findViewById(R.id.nohp);
        domisili = findViewById(R.id.domisili);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(Profile.this,
                        Home.class);
                startActivityForResult(addIntent,1);
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getDisplayName());

                if(user.getProfilePic().equals("default")){
                    pp.setImageResource(R.drawable.blank);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
