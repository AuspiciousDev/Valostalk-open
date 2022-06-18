package com.example.valostats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valostats.DAO.DAOUser;
import com.example.valostats.Model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegister, tvForgotPass;
    private TextInputEditText edtUserName, edtPassword;
    private Button btnLogin;
    private DAOUser daoUser;
    private User user;

    private String strUsername, strPassword;
    private final DatabaseReference Firebase = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference databaseReference = Firebase.child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtUserName = findViewById(R.id.login_edtUsername);
        edtPassword = findViewById(R.id.login_edtPassword);
        tvRegister = findViewById(R.id.login_tvRegister);
        tvForgotPass = findViewById(R.id.login_tvForgotPass);
        btnLogin = findViewById(R.id.login_btnLogin);

        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            finish();
        });
        tvForgotPass.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
        btnLogin.setOnClickListener(view -> {
            ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loading");
            pd.show();
            strUsername = edtUserName.getText().toString();
            strPassword = edtPassword.getText().toString();
            if (!strUsername.isEmpty()) {
                if (!strPassword.isEmpty()) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(strUsername).exists()) {
                                user = snapshot.child(strUsername).getValue(User.class);
                                if (strPassword.equals(user.getPassword())) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("loginUserName", strUsername);
                                    startActivity(intent);
                                    finish();
                                    pd.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Username/Password Error!", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "User Doesn't Exists!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Check fields!", Toast.LENGTH_SHORT).show();
                    edtPassword.setError("Empty Field!");
                    pd.dismiss();
                }

            } else {
                if (strPassword.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Check fields!", Toast.LENGTH_SHORT).show();
                    edtPassword.setError("Empty Field!");
                    pd.dismiss();
                }
                Snackbar.make(findViewById(android.R.id.content), "Check fields!", Toast.LENGTH_SHORT).show();
                edtUserName.setError("Empty Field!");
                pd.dismiss();
            }
        });
    }

}