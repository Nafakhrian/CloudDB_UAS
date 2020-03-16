package id.ub.sch.privateassignment.vokasi024.uas_bsdt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    EditText txtUsername, txtPassword, txtEmail;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = (ImageView) findViewById(R.id.btnRegBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        txtEmail = (EditText) findViewById(R.id.edtRegEma);
        txtPassword = (EditText) findViewById(R.id.edtRegPass);
        txtUsername = (EditText) findViewById(R.id.edtRegUser);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registProses(View v){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(txtUsername.getText().toString())
                                    .setPhotoUri(Uri.parse("https://randomuser.me/api/portraits/men/76.jpg"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                Intent homepage = new Intent(RegisterActivity.this, MenuActivity.class);
                                                startActivity(homepage);
                                                finish();
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
