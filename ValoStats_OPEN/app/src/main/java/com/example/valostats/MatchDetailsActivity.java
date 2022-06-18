package com.example.valostats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.valostats.Adapter.RVAMatch;
import com.example.valostats.Adapter.adapterBlueTeam;
import com.example.valostats.Adapter.adapterRedTeam;
import com.example.valostats.Model.Icon;
import com.example.valostats.Model.MatchHeaderDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MatchDetailsActivity extends AppCompatActivity {
    MatchHeaderDetails matchHeaderDetails;
    TextView matchID;
    private String strmatchID;
    private String apiURL = "https://api.henrikdev.xyz/valorant/v2/match/";
    private RecyclerView rvRed, rvBLue;
    private adapterRedTeam adapterRedTeam;
    private adapterBlueTeam adapterBlueTeam;
    private TextView tvMatchTime, tvMatchLength;
    private TextView tvMap, tvBlueRounds, tvRedRounds, tvBlueKDA, tvRedKDA, tvRedHS, tvBlueHS;
    private ImageView matchMapHeader;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        linearLayout = findViewById(R.id.lay_details);
        matchID = findViewById(R.id.details_tvMatchID);
        matchHeaderDetails = (MatchHeaderDetails) getIntent().getSerializableExtra("editEntry");
        strmatchID = matchHeaderDetails.getMatchID();

        matchMapHeader = findViewById(R.id.matchMapHeader);
        tvMap = findViewById(R.id.matchMap);
        tvBlueRounds = findViewById(R.id.matchBlueRounds);
        tvRedRounds = findViewById(R.id.matchRedRounds);

        tvMatchTime = findViewById(R.id.matchTime);
        tvMatchLength = findViewById(R.id.matchLength);

        tvBlueKDA = findViewById(R.id.blueKDA);
        tvRedKDA = findViewById(R.id.redKDA);

        tvBlueHS = findViewById(R.id.blueHSr);
        tvRedHS = findViewById(R.id.redHSr);


        rvRed = findViewById(R.id.rvRedTeam);
        rvRed.setHasFixedSize(true);
        rvBLue = findViewById(R.id.rvBlueTeam);
        rvBLue.setHasFixedSize(true);

        Log.d("Details ", matchHeaderDetails.getMap());
        matchID.setText(matchHeaderDetails.getMatchID() + "\n" + matchHeaderDetails.getMap());

        callAPI(getApplicationContext());
    }

    public void callAPI(Context context) {
        ProgressDialog pd = new ProgressDialog(MatchDetailsActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        apiURL = apiURL + strmatchID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiURL, null,
                response -> {
                    try {

                        ArrayList<MatchHeaderDetails> entries = new ArrayList<>();
                        String ApiResponse = response.getString("status");
                        JSONObject data = response.getJSONObject("data");
                        JSONObject metadata = data.getJSONObject("metadata");
                        String matchMap = metadata.getString("map");
                        String time = metadata.getString("game_start_patched");
                        String roundsTotal = metadata.getString("rounds_played");
                        double matchLength = metadata.getDouble("game_length");
                        matchLength = matchLength * 0.001;
                        matchLength = matchLength / 60;
                        int indexOfDecimal = String.valueOf(matchLength).indexOf(".");
                        String getDecimals = String.valueOf(matchLength).substring(indexOfDecimal);

                        double seconds = Double.parseDouble(getDecimals) * 60;

                        tvMatchLength.setText(String.valueOf(Math.floor(matchLength)).replace(".0", "") + "m " + String.valueOf(Math.floor(seconds)).replace(".0", "") + "s ");
                        setMapHeader(getApplicationContext(), matchMap, matchMapHeader);
                        String matchid = metadata.getString("matchid");
                        Log.d("APImatchID ", matchid);

                        ;
                        tvMatchTime.setText(convertTime(time));

                        JSONObject teams = data.getJSONObject("teams");
                        JSONObject tRed = teams.getJSONObject("red");
                        String rWon = tRed.getString("rounds_won");

                        JSONObject tBlue = teams.getJSONObject("blue");
                        String bWon = tBlue.getString("rounds_won");

                        tvMap.setText(matchMap);
                        tvRedRounds.setText(rWon);
                        tvBlueRounds.setText(bWon);

                        JSONObject players = data.getJSONObject("players");
                        JSONArray red = players.getJSONArray("red");
                        int rTotalKills = 0, rTotalDeaths = 0, rTotalAssists = 0;
                        double teamTotalHShots = 0, teamTotalShots = 0, teamTotalHS = 0;
                        for (int x = 0; x < red.length(); x++) {
                            matchHeaderDetails = new MatchHeaderDetails();
                            JSONObject playerInfo = red.getJSONObject(x);
                            String name = playerInfo.getString("name");
                            String tag = playerInfo.getString("tag");
                            String currRank = playerInfo.getString("currenttier_patched");
                            String character = playerInfo.getString("character");
                            Log.d("APImatchID ", name + " #" + tag + " | " + character + " | " + currRank);
                            String currentTier = playerInfo.getString("currenttier");
                            JSONObject assets = playerInfo.getJSONObject("assets");
                            JSONObject agent = assets.getJSONObject("agent");
                            String strAgent = agent.getString("small");
                            JSONObject stats = playerInfo.getJSONObject("stats");
                            int score = stats.getInt("score");
                            int kills = stats.getInt("kills");
                            int deaths = stats.getInt("deaths");
                            int assists = stats.getInt("assists");
                            int bodyshots = stats.getInt("bodyshots");
                            int headshots = stats.getInt("headshots");
                            int legshots = stats.getInt("legshots");
                            int damage_made = playerInfo.getInt("damage_made");
                            matchHeaderDetails.setRiotName(name);
                            matchHeaderDetails.setRiotTag(tag);
                            //matchHeaderDetails.setc(character);
                            matchHeaderDetails.setCharacter(character);
                            matchHeaderDetails.setScore(score);
                            matchHeaderDetails.setKills(kills);
                            matchHeaderDetails.setDeaths(deaths);
                            matchHeaderDetails.setAssists(assists);
                            matchHeaderDetails.setBodyshots(bodyshots);
                            matchHeaderDetails.setHeadshots(headshots);
                            matchHeaderDetails.setLegshots(legshots);
                            matchHeaderDetails.setDamageMade(damage_made);
                            matchHeaderDetails.setAgentIcon(strAgent);
                            matchHeaderDetails.setMatchTier(currentTier);
                            matchHeaderDetails.setAcs(score / (Integer.parseInt(roundsTotal)));
                            matchHeaderDetails.setAdr(damage_made / (Integer.parseInt(roundsTotal)));

                            double kda = (matchHeaderDetails.getKills() + matchHeaderDetails.getAssists());
                            matchHeaderDetails.setKdaRatio((double) Math.round((kda / matchHeaderDetails.getDeaths()) * 100) / 100);

                            double totalShot = bodyshots + legshots + headshots;
                            double hs = matchHeaderDetails.getHeadshots() / totalShot;
                            //teamTotalShots = teamTotalShots + totalShot;
                            teamTotalHShots = teamTotalHShots + (double) Math.round((matchHeaderDetails.getHeadshots() / totalShot) * 100);
                            matchHeaderDetails.setHeadshotratio((double) Math.round((matchHeaderDetails.getHeadshots() / totalShot) * 100));
                            rTotalKills = rTotalKills + kills;
                            rTotalDeaths = rTotalDeaths + deaths;
                            rTotalAssists = rTotalAssists + assists;

                            entries.add(matchHeaderDetails);
                        }
                        String RKDA = String.valueOf(rTotalKills) + " / " + String.valueOf(rTotalDeaths) + " / " + String.valueOf(rTotalAssists);
                        tvRedKDA.setText(RKDA);
                        //teamTotalHS = teamTotalHShots / teamTotalShots;
                        tvRedHS.setText(String.valueOf(teamTotalHShots / 5) + " HS%");


                        teamTotalHShots = 0;
                        teamTotalShots = 0;
                        LinearLayoutManager manager = new LinearLayoutManager(MatchDetailsActivity.this);
                        rvRed.setLayoutManager(manager);
                        adapterRedTeam = new adapterRedTeam(MatchDetailsActivity.this);
                        rvRed.setAdapter(adapterRedTeam);
                        adapterRedTeam.setItem(entries);


                        JSONArray blue = players.getJSONArray("blue");
                        ArrayList<MatchHeaderDetails> entries2 = new ArrayList<>();
                        int bTotalKills = 0, bTotalDeaths = 0, bTotalAssists = 0;
                        for (int x = 0; x < blue.length(); x++) {
                            matchHeaderDetails = new MatchHeaderDetails();
                            JSONObject playerInfo = blue.getJSONObject(x);
                            String name = playerInfo.getString("name");
                            String tag = playerInfo.getString("tag");
                            String currRank = playerInfo.getString("currenttier_patched");
                            String character = playerInfo.getString("character");
                            Log.d("APImatchID ", name + " #" + tag + " | " + character + " | " + currRank);
                            String currentTier = playerInfo.getString("currenttier");
                            JSONObject assets = playerInfo.getJSONObject("assets");
                            JSONObject agent = assets.getJSONObject("agent");
                            String strAgent = agent.getString("small");
                            JSONObject stats = playerInfo.getJSONObject("stats");
                            int score = stats.getInt("score");
                            int kills = stats.getInt("kills");
                            int deaths = stats.getInt("deaths");
                            int assists = stats.getInt("assists");
                            int bodyshots = stats.getInt("bodyshots");
                            int headshots = stats.getInt("headshots");
                            int legshots = stats.getInt("legshots");
                            int damage_made = playerInfo.getInt("damage_made");
                            matchHeaderDetails.setRiotName(name);
                            matchHeaderDetails.setRiotTag(tag);
                            //matchHeaderDetails.setc(character);
                            matchHeaderDetails.setCharacter(character);
                            matchHeaderDetails.setScore(score);
                            matchHeaderDetails.setKills(kills);
                            matchHeaderDetails.setDeaths(deaths);
                            matchHeaderDetails.setAssists(assists);
                            matchHeaderDetails.setBodyshots(bodyshots);
                            matchHeaderDetails.setHeadshots(headshots);
                            matchHeaderDetails.setLegshots(legshots);
                            matchHeaderDetails.setDamageMade(damage_made);
                            matchHeaderDetails.setAgentIcon(strAgent);
                            matchHeaderDetails.setMatchTier(currentTier);
                            matchHeaderDetails.setAcs(score / (Integer.parseInt(roundsTotal)));
                            matchHeaderDetails.setAdr(damage_made / (Integer.parseInt(roundsTotal)));

                            double kda = (matchHeaderDetails.getKills() + matchHeaderDetails.getAssists());
                            matchHeaderDetails.setKdaRatio((double) Math.round((kda / matchHeaderDetails.getDeaths()) * 100) / 100);

                            double totalShot = bodyshots + legshots + headshots;
                            double hs = matchHeaderDetails.getHeadshots() / totalShot;
                            matchHeaderDetails.setHeadshotratio((double) Math.round((matchHeaderDetails.getHeadshots() / totalShot) * 100));

                            //teamTotalShots = teamTotalShots + totalShot;
                            teamTotalHShots = teamTotalHShots + (double) Math.round((matchHeaderDetails.getHeadshots() / totalShot) * 100);

                            bTotalKills = bTotalKills + kills;
                            bTotalDeaths = bTotalDeaths + deaths;
                            bTotalAssists = bTotalAssists + assists;

                            entries2.add(matchHeaderDetails);
                        }
                        teamTotalHS = 0;
                        String BKDA = String.valueOf(bTotalKills) + " / " + String.valueOf(bTotalDeaths) + " /  " + String.valueOf(bTotalAssists);
                        tvBlueKDA.setText(BKDA);
                        tvBlueHS.setText(String.valueOf(teamTotalHShots / 5) + " HS%");

                        LinearLayoutManager manager2 = new LinearLayoutManager(MatchDetailsActivity.this);
                        rvBLue.setLayoutManager(manager2);
                        adapterBlueTeam = new adapterBlueTeam(MatchDetailsActivity.this);
                        rvBLue.setAdapter(adapterBlueTeam);
                        adapterBlueTeam.setItem(entries2);
                        linearLayout.setVisibility(View.VISIBLE);
                        pd.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setMapHeader(Context context, String mapName, ImageView imageView) {
        if (mapName.equals("Ascent")) {
            Picasso.with(context)
                    .load(R.drawable.view_ascent)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        } else if (mapName.equals("Bind")) {
            Picasso.with(context)
                    .load(R.drawable.view_bind)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        } else if (mapName.equals("Breeze")) {
            Picasso.with(context)
                    .load(R.drawable.view_breeze)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        } else if (mapName.equals("Fracture")) {
            Picasso.with(context)
                    .load(R.drawable.view_fracture)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        } else if (mapName.equals("Haven")) {
            Picasso.with(context)
                    .load(R.drawable.view_haven)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        } else if (mapName.equals("Icebox")) {
            Picasso.with(context)
                    .load(R.drawable.view_icebox)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        } else if (mapName.equals("Split")) {
            Picasso.with(context)
                    .load(R.drawable.view_split)
                    .placeholder(R.drawable.view_ascent)
                    .into(imageView);
        }
    }

    private String convertTime(String rawTime) {
        String time = "";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMMM d,yyyy hh:mm aaa");
            format1.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

            SimpleDateFormat format2 = new SimpleDateFormat("MMM d, yyyy hh:mm aaa");
            format2.setTimeZone(TimeZone.getDefault());

            Date past = format1.parse(rawTime);
            time = (format2.format(past));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}