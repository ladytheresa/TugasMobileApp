package umn.ac.tugasmobile;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetailUser extends AppCompatActivity {

    TextView namalengkap, tvdisplay, tvangkatan, tvnim, tvstatus, tvpekerjaan, tvnohp, tvdomisili;
    ImageView ivpp, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        final Intent i = getIntent();
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

        String nama = i.getStringExtra("nama");
        String displayname = i.getStringExtra("displayname");
        String angkatan = i.getStringExtra("angkatan");
        String pp = i.getStringExtra("pp");
        String nim = i.getStringExtra("nim");
        String status = i.getStringExtra("status");
        String pekerjaan = i.getStringExtra("pekerjaan");
        String nohp = i.getStringExtra("nohp");
        String domisili = i.getStringExtra("domisili");

        tvdisplay.setText(displayname);
        namalengkap.setText(nama);
        tvangkatan.setText(angkatan);
        tvnim.setText(nim);
        tvstatus.setText(status);
        tvpekerjaan.setText(pekerjaan);
        tvnohp.setText(nohp);
        tvdomisili.setText(domisili);

        if (pp.equals("default")) {
            ivpp.setImageResource(R.drawable.blank);
        } else {
            Glide.with(DetailUser.this).load(pp).into(ivpp);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fnama = namalengkap.getText().toString();
                String fnohp = tvnohp.getText().toString();
                ArrayList<ContentProviderOperation> ops = new ArrayList<>();

                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());


                if (fnama != null) {
                    ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                    fnama).build());
                }

                if (fnohp != null) {
                    ops.add(ContentProviderOperation.
                            newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, fnohp)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());
                }

                // Asking the Contact provider to create a new contact
                try {
                    if(contactExists(DetailUser.this,fnohp)==true){
                        Toast.makeText(DetailUser.this, "Contact already exists!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        Toast.makeText(DetailUser.this, "Succesfully added to contact!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailUser.this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public boolean contactExists(Context context, String number) {
//  number is the phone number
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }
}
