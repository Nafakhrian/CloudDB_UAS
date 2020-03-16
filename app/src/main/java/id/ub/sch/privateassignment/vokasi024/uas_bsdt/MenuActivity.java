package id.ub.sch.privateassignment.vokasi024.uas_bsdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    Button mhs, buku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mhs = (Button) findViewById(R.id.mahasiswa);
        mhs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(view.getContext(),MahasiswaActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        buku = (Button) findViewById(R.id.buku);
        buku.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(view.getContext(),BukuActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    public void prosesLogout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent pindah = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(pindah);
    }
}
