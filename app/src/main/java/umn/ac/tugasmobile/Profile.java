package umn.ac.tugasmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static java.security.AccessController.getContext;

public class Profile extends AppCompatActivity {

    ImageView back,pp;
    TextView username;
    EditText fullname,nim,angkatan,Status,Pekerjaan,nohp,domisili;
    Button update, logout;

    FirebaseUser firebaseUser;
    DatabaseReference userRef;
    private Uri mImageUri = null;
    private static final  int GALLERY_REQUEST =1;
    private static final int CAMERA_REQUEST_CODE=1;
    private StorageReference mStorage;
    private StorageTask uploadTask;

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
        logout = findViewById(R.id.btnLogout);
        update = findViewById(R.id.btnUpdate);

        mStorage = FirebaseStorage.getInstance().getReference("uploads").child(firebaseUser.getUid());

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
                fullname.setText(user.getNamalengkap());
                nim.setText(user.getNim());
                angkatan.setText(user.getAngkatan());
                Status.setText(user.getStatusPekerjaan());
                Pekerjaan.setText(user.getPekerjaan());
                nohp.setText(user.getNomorhp());
                domisili.setText(user.getDomisili());

                if(user.getProfilePic().equals("default")){
                    pp.setImageResource(R.drawable.blank);
                }
                else{
                    Glide.with(Profile.this).load(user.getProfilePic()).into(pp);
                }
                final String uid = user.getUid();
                final String displayname = user.getDisplayName();
                final String profilepic = user.getProfilePic();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "Logged out",Toast.LENGTH_SHORT).show();
                Intent addIntent = new Intent(Profile.this,
                        MainActivity.class);
                startActivityForResult(addIntent,1);
                finish();
                return;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        String namalengkap = ((EditText) findViewById(R.id.fullname)).getText().toString();
                        String nim = ((EditText) findViewById(R.id.nim)).getText().toString();
                        String angkatan = ((EditText) findViewById(R.id.angkatan)).getText().toString();
                        String statuspekerjaan = ((EditText) findViewById(R.id.Status)).getText().toString();
                        String pekerjaan = ((EditText) findViewById(R.id.Pekerjaan)).getText().toString();
                        String nohp = ((EditText) findViewById(R.id.nohp)).getText().toString();
                        String domisili = ((EditText) findViewById(R.id.domisili)).getText().toString();
                        String uid = user.getUid();
                        String displayname = user.getDisplayName();
                        String profilepic = user.getProfilePic();
                        String email = user.getEmail();

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
                        userRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Profile.this, "Update data successful!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Profile.this, "Update fail!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");
        pd.show();

        if(mImageUri!=null){
            final StorageReference fileReference = mStorage.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));
            uploadTask = fileReference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("profilepic", mUri);

                        userRef.updateChildren(map);
                        pd.dismiss();
                    }
                    else{
                        Toast.makeText(Profile.this, "Failed to upload image to database",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else{
            Toast.makeText(Profile.this, "No image selected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null&&data.getData()!=null){
            mImageUri=data.getData();
            if(uploadTask!=null&&uploadTask.isInProgress()){
                Toast.makeText(Profile.this, "Upload in progress",Toast.LENGTH_SHORT).show();

            }else{
                uploadImage();
            }

        }
    }
}
