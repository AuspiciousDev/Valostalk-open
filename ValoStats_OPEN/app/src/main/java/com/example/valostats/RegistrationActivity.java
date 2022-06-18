package com.example.valostats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valostats.DAO.DAOUser;
import com.example.valostats.Model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private TextView tvLogin;
    private Button btnRegister;
    private EditText edtUsername, edtEmail, edtPass, edtPassConf, edtBday;
    private String strUsername, strEmail, strBirthday, strPass, strPassConf;
    private User user;
    private DAOUser daoUser;
    // private boolean bool = true;

    final private String Alphabets = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    final private String Numerical = "0123456789";
    private SecureRandom rnd = new SecureRandom();
    final private Calendar calendar = Calendar.getInstance();

    private DatabaseReference Firebase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference = Firebase.child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getUtils();
        Bday_ShowCalendar();

        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
        btnRegister.setOnClickListener(view -> {
            //  CheckUser();
            if (CheckFields() == true) {
                if (CheckPassword() == true) {
                    CheckUser();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Password doesn't match!", Snackbar.LENGTH_SHORT).show();
                    edtPass.setError("Password doesn't match.");
                    edtPassConf.setError("Password doesn't match.");
                }
            }
        });
    }

    private void CheckUser() {
        ProgressDialog pd1 = new ProgressDialog(RegistrationActivity.this);
        pd1.setMessage("Checking User");
        pd1.show();
        getValues();
        if (strUsername.isEmpty()) {
            edtUsername.setError("Empty Field!");
        } else {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(strUsername).exists()) {
                        Snackbar.make(findViewById(android.R.id.content), "User exists!", Snackbar.LENGTH_SHORT).show();
                        pd1.dismiss();
                    } else {
                        Register();
                        pd1.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    pd1.dismiss();
                }
            });
        }
    }

    private Boolean CheckFields() {
        boolean bool = true;
        getValues();
        if (strPass.isEmpty() && strPassConf.isEmpty() && strEmail.isEmpty() && strBirthday.isEmpty()) {
            bool = false;
        } else {

            if (strPass.isEmpty()) {
                bool = false;
                edtPass.setError("Empty Field!");
                if (strPass.length() <= 8) {
                    edtPass.setError("At least 8 characters");
                }
            }
            if (strPassConf.isEmpty()) {
                bool = false;
                edtPassConf.setError("Empty Field!");
            }
            if (strEmail.isEmpty()) {
                bool = false;
                edtEmail.setError("Empty Field!");
            } else {
                if (isEmailValid(strEmail) == false) {
                    bool = false;
                    edtEmail.setError("Invalid Email!");
                }
            }
            if (strBirthday.isEmpty()) {
                bool = false;
                edtBday.setError("Empty Field!");
            }
        }
        return bool;
    }

    private boolean isEmailValid(CharSequence charSequence) {
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    private Boolean CheckPassword() {
        getValues();
        boolean bool = true;
        if (!strPass.equals(strPassConf)) {
            bool = false;
        }
        return bool;
    }

    private void Register() {
        ProgressDialog pd = new ProgressDialog(RegistrationActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        getValues();
        String UUID = randomString(5) + "-" + randomString1(10) + "-" + randomString(5);
        User user = new User(UUID, strUsername, strPass, strBirthday, strEmail);
        daoUser = new DAOUser();
        daoUser.add(user).addOnSuccessListener(suc -> {
            Snackbar.make(findViewById(android.R.id.content), "Registered Successfully!", Snackbar.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
            pd.dismiss();
        }).addOnFailureListener(er -> {
            Snackbar.make(findViewById(android.R.id.content), er.toString(), Snackbar.LENGTH_SHORT).show();
            pd.dismiss();
        });

    }

    private String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(Numerical.charAt(rnd.nextInt(Numerical.length())));
        return sb.toString();

    }

    private String randomString1(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(Alphabets.charAt(rnd.nextInt(Alphabets.length())));
        return sb.toString();

    }

    private void Bday_ShowCalendar() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        edtBday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(RegistrationActivity.this, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        edtBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegistrationActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDate() {
        String myFormat = "MMMM dd, yyyy"; //put your date format in which you need to display
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        edtBday.setText(sdf.format(calendar.getTime()));
    }

    private void getValues() {
        strUsername = edtUsername.getText().toString();
        strEmail = edtEmail.getText().toString();
        strPass = edtPass.getText().toString();
        strPassConf = edtPassConf.getText().toString();
        strBirthday = edtBday.getText().toString();
    }

    private void getUtils() {
        edtUsername = findViewById(R.id.reg_edtUserName);
        edtEmail = findViewById(R.id.reg_edtEmail);
        edtPass = findViewById(R.id.reg_edtPass);
        edtPassConf = findViewById(R.id.reg_edtPassConf);
        edtBday = findViewById(R.id.reg_edtBirthday);
        tvLogin = findViewById(R.id.regis_tvLogin);
        btnRegister = findViewById(R.id.regis_btnRegister);
    }
}