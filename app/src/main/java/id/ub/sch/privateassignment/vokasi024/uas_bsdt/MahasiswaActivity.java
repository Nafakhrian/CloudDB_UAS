package id.ub.sch.privateassignment.vokasi024.uas_bsdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity {

    EditText nim, nama, alamat, jurusan;
    Spinner jeniskelamin;
    ImageView back;
    ListView listMhs;

    ArrayList<String> listitem = new ArrayList<String>();
    ArrayList<String> listkey = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private static final String TAG = "MahasiswaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        nim = (EditText) findViewById(R.id.nim);
        nama = (EditText) findViewById(R.id.nama);
        alamat = (EditText) findViewById(R.id.alamat);
        jurusan = (EditText) findViewById(R.id.jurusan);
        back = (ImageView) findViewById(R.id.btnMahaBack);
        jeniskelamin = (Spinner) findViewById(R.id.jeniskelamin);
        listMhs = (ListView) findViewById(R.id.listview);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),MenuActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listitem);
        listMhs.setAdapter(adapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Mahasiswa");

        listMhs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selected = position;
                myRef.child(listkey.get(position)).removeValue();
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("nim").getValue().toString();
                String value1 = dataSnapshot.child("nama").getValue().toString();
                listitem.add(value + " - " + value1);
                listkey.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                int index = listkey.indexOf(key);

                if (index !=-1){
                    listitem.remove(index);
                    listkey.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tambahData(View view){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mahasiswa");

        String nimM = nim.getText().toString();
        String namaM = nama.getText().toString();
        String alamatM = alamat.getText().toString();
        String jurusanM = jurusan.getText().toString();
        String jk = jeniskelamin.getSelectedItem().toString();

        myRef.push().setValue(new Mahasiswa(nimM, namaM, alamatM, jurusanM, jk,false  ));
        nim.setText("");
        nama.setText("");
        alamat.setText("");
        jurusan.setText("");
        jeniskelamin.setSelected(false);
        Toast.makeText(this, "Data sudah tersimpan", Toast.LENGTH_LONG).show();
    }
}
