package umn.ac.tugasmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private Button register, cancel;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        register = findViewById(R.id.btnRegister);
        cancel = findViewById(R.id.btnCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(Register.this, MainActivity.class);
                startActivityForResult(addIntent,1);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
                String password = ((EditText) findViewById(R.id.etPassword)).getText().toString();
                final String angkatan = ((EditText) findViewById(R.id.etAngkatan)).getText().toString();
                final String domisili = ((EditText) findViewById(R.id.etDomisili)).getText().toString();
                final String namalengkap = ((EditText) findViewById(R.id.etNama)).getText().toString();
                final String nim = ((EditText) findViewById(R.id.etNIM)).getText().toString();
                final String nohp = ((EditText) findViewById(R.id.etNomorHp)).getText().toString();
                final String pekerjaan = ((EditText) findViewById(R.id.etPekerjaan)).getText().toString();
                final String statuspekerjaan = ((EditText) findViewById(R.id.etStatus)).getText().toString();
                final String profilepic = "default";
                final String displayname = ((EditText) findViewById(R.id.etDisplayName)).getText().toString();

                //String email = email.getText().toString().trim();
                //String password = password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Register.this, "User successfully registered! You can now log in!", Toast.LENGTH_SHORT).show();
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = firebaseUser.getUid();

                                mDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("uid",uid);
                                hashMap.put("nim",nim);
                                hashMap.put("displayname",displayname);
                                hashMap.put("namalengkap",namalengkap);
                                hashMap.put("domisili",domisili);
                                hashMap.put("angkatan",angkatan);
                                hashMap.put("email",email);
                                hashMap.put("nomorhp",nohp);
                                hashMap.put("pekerjaan",pekerjaan);
                                hashMap.put("profilepic",profilepic);
                                hashMap.put("statuspekerjaan",statuspekerjaan);

                                    mDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(Register.this, MainActivity.class));
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(Register.this, "Register fail!" + task.getException(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                            }
                        });

            }
        });


    } //oncreate

}
