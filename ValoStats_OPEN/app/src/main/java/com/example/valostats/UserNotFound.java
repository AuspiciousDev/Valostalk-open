package com.example.valostats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UserNotFound extends AppCompatActivity {

    private Button btnSearch;
    private Intent intent;
    private String strSearch;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_not_found);

        intent = getIntent();
        strSearch = intent.getExtras().getString("riotUsername");
        if (strSearch.equals(null)) {
            startActivity(new Intent(UserNotFound.this, LoginActivity.class));
        }
        textView = findViewById(R.id.u404_tvName);
        btnSearch = findViewById(R.id.notFound_btnSearch);
        textView.setText(strSearch);
        btnSearch.setOnClickListener(view -> {
            startActivity(new Intent(UserNotFound.this, MainActivity.class));
            finish();
        });
    }


}