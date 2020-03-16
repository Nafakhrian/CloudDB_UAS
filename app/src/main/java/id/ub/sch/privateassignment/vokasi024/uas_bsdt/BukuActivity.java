package id.ub.sch.privateassignment.vokasi024.uas_bsdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class BukuActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference buku_ref = db.collection("Buku");
    EditText edit_judul;
    EditText edit_pengarang;
    EditText edit_penerbit;
    EditText edit_stock;
    ListView list_buku;
    ImageView btnBukuBack;
    ArrayList<Buku> buku_item = new ArrayList<>();
    BukuAdapter adapter;
    private String selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);

        edit_judul = findViewById(R.id.edit_judul);
        edit_pengarang = findViewById(R.id.edit_pengarang);
        edit_penerbit = findViewById(R.id.edit_penerbit);
        edit_stock = findViewById(R.id.edit_stock);
        list_buku = findViewById(R.id.list_buku);
        btnBukuBack = findViewById(R.id.btnBukuBack);

        btnBukuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),MenuActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        list_buku.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Buku buku = adapter.getItem(position);
                edit_judul.setText(buku.getJudul());
                edit_pengarang.setText(buku.getPengarang());
                edit_penerbit.setText(buku.getPenerbit());
                edit_stock.setText(buku.getStock());
                selectedId = buku.getId();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        buku_ref.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    return;
                }
                buku_item.clear();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Buku buku = documentSnapshot.toObject(Buku.class);
                    buku.setId(documentSnapshot.getId());
                    buku_item.add(buku);
                }

                adapter = new BukuAdapter(BukuActivity.this, buku_item);
                adapter.notifyDataSetChanged();
                list_buku.setAdapter(adapter);
            }
        });
    }

    public void addData(View view){
        String judul = edit_judul.getText().toString();
        String pengarang = edit_pengarang.getText().toString();
        String penerbit = edit_penerbit.getText().toString();
        String stock = edit_stock.getText().toString();

        Map<String,Object> buku = new HashMap<>();
        buku.put("judul",judul);
        buku.put("pengarang",pengarang);
        buku.put("penerbit",penerbit);
        buku.put("stock",stock);

        buku_ref.add(buku)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        edit_judul.setText("");
                        edit_pengarang.setText("");
                        edit_penerbit.setText("");
                        edit_stock.setText(null);
                        Toast.makeText(BukuActivity.this, "Success", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BukuActivity.this, "Error "+e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void editData(View view){
        String judul = edit_judul.getText().toString();
        String pengarang = edit_pengarang.getText().toString();
        String penerbit = edit_penerbit.getText().toString();
        String stock = edit_stock.getText().toString();

        Buku buku = new Buku(judul,pengarang,penerbit,stock);
        buku_ref.document(selectedId).set(buku)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edit_judul.setText(null);
                        edit_pengarang.setText(null);
                        edit_penerbit.setText(null);
                        edit_stock.setText(null);
                        Toast.makeText(BukuActivity.this, "Updated ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BukuActivity.this, "Error "+e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void deleteData(View view){

        buku_ref.document(selectedId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edit_judul.setText(null);
                        edit_pengarang.setText(null);
                        edit_penerbit.setText(null);
                        edit_stock.setText(null);
                        Toast.makeText(BukuActivity.this, "Deleted ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BukuActivity.this, "Error "+e, Toast.LENGTH_LONG).show();
                    }
                });
    }

}
