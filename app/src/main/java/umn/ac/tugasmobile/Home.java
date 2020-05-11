package umn.ac.tugasmobile;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    SharedPreferences pref; // 0 - for private mode
    SharedPreferences.Editor editor;

    ImageView pp;
    TextView profile, currentstatus;
    EditText userstatus;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, userRef;
    List<User> list =new ArrayList<>();
    Context context;
    Button updatestatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profile = findViewById(R.id.username);
        pp = findViewById(R.id.pp);
        updatestatus = findViewById(R.id.updateStatus);
        userstatus = findViewById(R.id.userstatus);
        recyclerView=findViewById(R.id.rvUser);
        currentstatus = findViewById(R.id.currentstatus);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Home.this, 3);
        recyclerView.setLayoutManager(layoutManager);

        final UserAdapter userAdapter=new UserAdapter(this, list);
        recyclerView.setAdapter(userAdapter);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        pref = getApplicationContext().getSharedPreferences("status", 0);
        currentstatus.setText(pref.getString("status", null));

        if (firebaseUser == null) {
            Toast.makeText(Home.this, "Welcome, please log in or register",
                    Toast.LENGTH_SHORT).show();
            Intent addIntent = new Intent(Home.this,
                    MainActivity.class);
            startActivityForResult(addIntent,1);
        }

        updatestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = pref.edit();
                editor.putString("status", userstatus.getText().toString());
                editor.commit();
                finish();
                startActivity(getIntent());
            }
        });


        userRef= FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

        databaseReference= FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User user=dataSnapshot1.getValue(User.class);

                    list.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profile.setText(user.getDisplayName());

                if(user.getProfilePic().equals("default")){
                    pp.setImageResource(R.drawable.blank);
                }
                else{
                    Glide.with(Home.this).load(user.getProfilePic()).into(pp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(Home.this,
                        Profile.class);
                startActivityForResult(addIntent,1);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(Home.this,
                        Profile.class);
                startActivityForResult(addIntent,1);
            }
        });


    }


}
