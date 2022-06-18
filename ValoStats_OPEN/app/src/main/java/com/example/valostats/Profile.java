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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.valostats.Model.MatchHeaderDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Profile extends AppCompatActivity {

    private TextView tb;
    private ImageView img;
    private TextInputEditText edtSearchUName, edtgetSearchUTag;
    private Button btnSearch;

    private String MainURL = "https://api.henrikdev.xyz/valorant/v1/";
    private String AccountInfoURL;
    private String getSearchRiotID, getSearchUName, getSearchUTag;
    MatchHeaderDetails matchHeaderDetails;
    Intent intent;
    String strmatchID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tb = findViewById(R.id.profile_tv);
        img = findViewById(R.id.imageView);
        edtSearchUName = findViewById(R.id.profile_edtSearchID);
        edtgetSearchUTag = findViewById(R.id.profile_edtSearchTag);
        intent = getIntent();
        strmatchID = intent.getExtras().getString("RIOTID");
        Log.d("StartAct : ", strmatchID);
        Toast.makeText(getApplicationContext(), "Error : " + strmatchID, Toast.LENGTH_LONG).show();
        btnSearch = findViewById(R.id.profile_btnSearch);

        btnSearch.setOnClickListener(view -> {
            getSearchUName = edtSearchUName.getText().toString();
            getSearchUTag = edtgetSearchUTag.getText().toString();

            AccountInfoURL = MainURL + "account/" + getSearchUName + "/" + getSearchUTag;
          //  callAPI(getApplicationContext());
        });
    }


}
