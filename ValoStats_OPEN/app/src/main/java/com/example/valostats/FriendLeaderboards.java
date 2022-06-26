package com.example.valostats;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.valostats.Adapter.RVAMatch;
import com.example.valostats.Adapter.adapterFriendsLeaderboard;
import com.example.valostats.DAO.DAOFriendsLeaderBoard;
import com.example.valostats.Model.AccountData;
import com.example.valostats.Model.AccountRank;
import com.example.valostats.Model.MatchHeaderDetails;
import com.example.valostats.Model.ResultHeader;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FriendLeaderboards extends AppCompatActivity {
    private String[] players = {"Limits#1010", "Jabolee#87000", "Rezkcimhcs#SMZ01", "Venzee#rise", "Doja Kakai#hehe", "Doja Kakai#hehe"};
    private RecyclerView recyclerView;
    private ResultHeader resultHeader;
    private AccountData accountData;
    private AccountRank accountRank;
    private String strName, strTag;
    private String APIUrl, APIUrl_AccountInfo;
    private Button btnUpdate, btnShow, btnSearch;
    private TextInputEditText edtSearch;
    private String riotName, riotTag, riotAccount;
    private adapterFriendsLeaderboard adapterFriendsLeaderboard;
    private DAOFriendsLeaderBoard daoFriendsLeaderBoard;

    private final DatabaseReference Firebase = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference databaseReference = Firebase.child("Friends");

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_leaderboards);
        getUtils();
        daoFriendsLeaderBoard = new DAOFriendsLeaderBoard();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Show();
            }
        });

        btnSearch.setOnClickListener(view -> {
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                // TODO: handle exception
            }
            riotAccount = edtSearch.getText().toString();
            SplitRiotTag(riotAccount);
            searchMainProfile(getApplicationContext(), strName, strTag);
            edtSearch.setText("");
        });
    }

    @Override
    protected void onStart() {
        getUtils();
        Show();
        super.onStart();
    }

    private void Show() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ResultHeader> entries = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ResultHeader resultHeader = snapshot.getValue(ResultHeader.class);
                    AccountRank accountRank = snapshot.child("rank").getValue(AccountRank.class);
                    resultHeader.setCurrenttier(accountRank.getCurrenttier());
                    resultHeader.setCurrenttierpatched(accountRank.getCurrenttierpatched());
                    resultHeader.setRanking_in_tier(accountRank.getRanking_in_tier());
                    resultHeader.setMmr_change_to_last_game(accountRank.getMmr_change_to_last_game());
                    entries.add(resultHeader);
                    Collections.sort(entries, Comparator.comparing(ResultHeader::getCurrenttier)
                            .thenComparing(ResultHeader::getRanking_in_tier).reversed());
//                    Collections.sort(entries, new Comparator<ResultHeader>() {
//                        @Override
//                        public int compare(ResultHeader a, ResultHeader b) {
//                            return Integer.compare(b.getCurrenttier(), a.getCurrenttier());
//                        }
//                    });
                }
                LinearLayoutManager manager = new LinearLayoutManager(FriendLeaderboards.this);
                recyclerView.setLayoutManager(manager);
                adapterFriendsLeaderboard = new adapterFriendsLeaderboard(FriendLeaderboards.this);
                recyclerView.setAdapter(adapterFriendsLeaderboard);
                adapterFriendsLeaderboard.setItem(entries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Update() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ResultHeader resultHeader = snapshot.getValue(ResultHeader.class);
                    getRankInfo(resultHeader.getRegion(), resultHeader.getPuuid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchMainProfile(Context context, String riotName, String riotTag) {
        APIUrl = "https://api.henrikdev.xyz/valorant/v1/";
        APIUrl_AccountInfo = APIUrl + "account/" + riotName + "/" + riotTag;
        ProgressDialog pd1 = new ProgressDialog(FriendLeaderboards.this);
        pd1.setMessage("Loading Profile info!!");
        pd1.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl_AccountInfo, null,
                response -> {
                    try {
                        accountData = new AccountData();
                        JSONObject data = response.getJSONObject("data");
                        JSONObject jsonCard = data.getJSONObject("card");
                        accountData.setPuuid(data.getString("puuid"));
                        accountData.setName(data.getString("name"));
                        accountData.setTag(data.getString("tag"));
                        accountData.setLevel(data.getString("account_level"));
                        accountData.setRegion(data.getString("region"));
                        accountData.setCardSmallUrl(jsonCard.getString("small"));
                        daoFriendsLeaderBoard.addAccountData(accountData).addOnSuccessListener(suc -> {
                            Snackbar.make(findViewById(android.R.id.content), "Registered Successfully!", Snackbar.LENGTH_SHORT).show();
                            Update();
                        }).addOnFailureListener(er -> {
                            Snackbar.make(findViewById(android.R.id.content), "Registered Failed!", Snackbar.LENGTH_SHORT).show();
                        });
                        Log.d("API-PlayerPUUID :", accountData.getPuuid());
                        Log.d("API-PlayerName :", accountData.getName());
                        Log.d("API-PlayerTag :", accountData.getTag());
                        Log.d("API-PlayerCard :", accountData.getCardSmallUrl());

                        pd1.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                        pd1.dismiss();
                    }
                },
                error -> {
                    pd1.dismiss();
                    Intent intent = new Intent(this, UserNotFound.class);
                    intent.putExtra("riotUsername", strName + "#" + strTag);
                    startActivity(intent);
                    finish();
                });
        requestQueue.add(jsonObjectRequest);

    }

    private void getRankInfo(String region, String puuid) {

        //rankInfo by PUUID
        //https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr/ap/4d4b6dfe-98fd-55de-99b0-44d0392aea63

        //rankInfo by riotName
        //https://api.henrikdev.xyz/valorant/v1/mmr/ap/name/tag
        //APIUrl = "https://api.henrikdev.xyz/valorant/v1/mmr/";
        //APIUrl_AccountInfo = APIUrl + region + "/" + riotName + "/" + riotTag;

        APIUrl = "https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr/";
        APIUrl_AccountInfo = APIUrl + region + "/" + puuid;

        ProgressDialog pd2 = new ProgressDialog(FriendLeaderboards.this);
        pd2.setMessage("Loading Rank Info!");
        pd2.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl_AccountInfo, null,
                response -> {
                    try {
                        accountRank = new AccountRank();
                        JSONObject data = response.getJSONObject("data");
                        String status = response.getString("status");
                        accountRank.setCurrenttier(data.getInt("currenttier"));
                        accountRank.setCurrenttierpatched(data.getString("currenttierpatched"));
                        accountRank.setRanking_in_tier(data.getInt("ranking_in_tier"));
                        accountRank.setMmr_change_to_last_game(data.getInt("mmr_change_to_last_game"));
                        daoFriendsLeaderBoard.addAccountRank(accountRank, puuid).addOnSuccessListener(suc -> {
                            Snackbar.make(findViewById(android.R.id.content), "Rank Obtained!", Snackbar.LENGTH_SHORT).show();
                        }).addOnFailureListener(er -> {
                            Snackbar.make(findViewById(android.R.id.content), "Rank Acquiring Failed!", Snackbar.LENGTH_SHORT).show();
                        });
                        Log.d("API-Status : ", status);
                        Log.d("API API-PlayerRR :", String.valueOf(accountRank.getCurrenttier()));
                        Log.d("API API-PlayerTier :", String.valueOf(accountRank.getCurrenttierpatched()));
                        Log.d("API API-PlayerActRank :", String.valueOf(accountRank.getRanking_in_tier()));
                        pd2.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        pd2.dismiss();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "Server Timeout!", Toast.LENGTH_LONG).show();
            pd2.dismiss();
            onBackPressed();
        });
        requestQueue.add(jsonObjectRequest);
        pd2.dismiss();
    }

    private void getUtils() {
        swipeRefreshLayout = findViewById(R.id.leaderboard_lyRefresh);
        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        btnUpdate = findViewById(R.id.leaderboard_btnUpdate);
        btnShow = findViewById(R.id.leaderboard_btnSHOW);
        btnSearch = findViewById(R.id.leaderboard_btnSearch);
        edtSearch = findViewById(R.id.leaderboard_edtSearch);
        linearLayout = findViewById(R.id.leaderboard_lyLeaderboards);
    }

    private void SplitRiotTag(String riotName) {
        String[] splitSearch = riotName.split("#");
        strName = splitSearch[0];
        strTag = splitSearch[1];
    }
}