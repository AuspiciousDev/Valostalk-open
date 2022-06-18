package com.example.valostats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.valostats.Adapter.RVAMatch;
import com.example.valostats.Model.Icon;
import com.example.valostats.Model.MatchHeaderDetails;
import com.example.valostats.Model.ResultHeader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class searchResultActivity extends AppCompatActivity {
    //API
    private String APIUrl;
    private String APIUrl_AccountInfo;

    //Model
    private ResultHeader resultHeader;
    private MatchHeaderDetails matchHeaderDetails;
    private Icon icon;

    private LinearLayout linearLayout;
    private TextView tvProfileRiotName, tvProfileLevel;
    private ImageView imgProfileImage, imgCoverImage, imgTier;
    private String strSearch, strName, strTag;
    private Intent intent;
    private TextView tvRankWithElo, tvWinRatio, tvKDARatioandWinLose;
    private String arrRRchanges[] = new String[5];
    private String arrMatchAgents[] = new String[5];
    private String arrMatchAgentsandPlayed[][];
    private String matchType;
    int totalGames;
    private TextView tvAgent1, tvAgent2, tvAgent3;
    private ImageView imgAgent1, imgAgent2, imgAgent3;
    //
    private RVAMatch rvaMatch;
    private RecyclerView recyclerView;
    private static final DecimalFormat df = new DecimalFormat("0");
    private static final DecimalFormat df2 = new DecimalFormat("0");

    private FloatingActionButton fab;
    private String MyPref = "linkedAccount";
    private String nameKey = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharePrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        icon = new Icon();
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        strSearch = sharedPreferences.getString("searchKey", "");
        Log.d("strSearch", strSearch);
        intent = getIntent();
        nameKey = intent.getStringExtra("riotUsername");
        if (nameKey == null) {
            SplitRiotTag(strSearch);
        } else {
            strSearch = nameKey;
            SplitRiotTag(strSearch);
        }
        getUtils();
        matchHeaderDetails = new MatchHeaderDetails();
        searchMainProfile(getApplicationContext(), strName, strTag);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(searchResultActivity.this, MainActivity.class));
            finish();
        });
    }

    private void searchMainProfile(Context context, String riotName, String riotTag) {
        resultHeader = new ResultHeader();
        APIUrl = "https://api.henrikdev.xyz/valorant/v1/";
        APIUrl_AccountInfo = APIUrl + "account/" + riotName + "/" + riotTag;

        ProgressDialog pd1 = new ProgressDialog(searchResultActivity.this);
        pd1.setMessage("Loading Profile info!!");
        pd1.show();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl_AccountInfo, null,
                response -> {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        JSONObject jsonCard = data.getJSONObject("card");
                        resultHeader.setPuuid(data.getString("puuid"));
                        resultHeader.setName(data.getString("name"));
                        resultHeader.setTag(data.getString("tag"));
                        resultHeader.setLevel(data.getString("account_level"));
                        resultHeader.setRegion(data.getString("region"));
                        resultHeader.setCardSmallUrl(jsonCard.getString("small"));

                        Log.d("API-PlayerPUUID :", resultHeader.getPuuid());
                        Log.d("API-PlayerName :", resultHeader.getName());
                        Log.d("API-PlayerTag :", resultHeader.getTag());
                        Log.d("API-PlayerCard :", resultHeader.getCardSmallUrl());

                        pd1.dismiss();
                        getRankInfo(getApplicationContext(), riotName, riotTag);
                        getRRchanges(getApplicationContext(), riotName, riotTag);
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

    private void getRRchanges(Context context, String riotName, String riotTag) {
        // https://api.henrikdev.xyz/valorant/v1/mmr-history/ap/Limits/1010
        APIUrl = "https://api.henrikdev.xyz/valorant/v1/mmr-history/ap/" + riotName + "/" + riotTag;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl, null,
                response -> {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Log.d("API Match Icon Status ", String.valueOf(response.getString("status")));
                        for (int x = 0; x < 5; x++) {
                            JSONObject match = data.getJSONObject(x);
                            Log.d("APIArray", String.valueOf(data.length()) + "| |" + String.valueOf(x));
                            Log.d("APIArrayRR", match.getString("mmr_change_to_last_game"));
                            arrRRchanges[x] = match.getString("mmr_change_to_last_game");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getRankInfo(Context context, String riotName, String riotTag) {
        // https://api.henrikdev.xyz/valorant/v1/mmr/ap/name/tag
        APIUrl = "https://api.henrikdev.xyz/valorant/v1/mmr/ap";
        APIUrl_AccountInfo = APIUrl + "/" + riotName + "/" + riotTag;

        ProgressDialog pd2 = new ProgressDialog(searchResultActivity.this);
        pd2.setMessage("Loading Rank Info!");
        pd2.show();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl_AccountInfo, null,
                response -> {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String status = response.getString("status");
                        resultHeader.setCurrenttier(data.getInt("currenttier"));
                        resultHeader.setCurrenttierpatched(data.getString("currenttierpatched"));
                        resultHeader.setRanking_in_tier(data.getInt("ranking_in_tier"));

                        Log.d("API-PlayerRank : ", status);
                        Log.d("API API-PlayerRR :", String.valueOf(resultHeader.getCurrenttier()));
                        Log.d("API API-PlayerTier :", String.valueOf(resultHeader.getCurrenttier()));
                        Log.d("API API-PlayerActRank :", String.valueOf(resultHeader.getCurrenttier()));

                        getRankIcon(getApplicationContext(), resultHeader.getCurrenttier());
                        pd2.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                        pd2.dismiss();
                    }
                }, error -> {
            Toast.makeText(context, "Server Timeout!", Toast.LENGTH_LONG).show();
            pd2.dismiss();
            onBackPressed();
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getRankIcon(Context context, int tierrrrr) {
        APIUrl = "https://valorant-api.com/v1/competitivetiers";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl, null,
                response -> {
                    try {
                        icon = new Icon();
                        JSONArray data = response.getJSONArray("data");
                        JSONObject tier = data.getJSONObject(3);
                        JSONArray EpTier = tier.getJSONArray("tiers");

                        Log.d("API Match Icon Status ", String.valueOf(response.getString("status")));
                        Log.d("API Match Icon tier 1", String.valueOf(tier));
                        Log.d("API Match Icon Lenght ", String.valueOf(EpTier.length()));

                        for (int x = 0; x < EpTier.length(); x++) {
                            JSONObject tier3 = EpTier.getJSONObject(x);
                            int tierNum = tier3.getInt("tier");

                            Log.d("API Match Icon tier 3", String.valueOf(tier3));
                            Log.d("APIMatchIconTierRAnk ", String.valueOf(tierrrrr));

                            if (tierNum == tierrrrr) {
                                icon.setTierIcon(tier3.getString("smallIcon"));
                                icon.setTierColor(tier3.getString("color"));
                                Log.d("API Match Icon ", icon.getTierIcon());
                                Log.d("API Match Color ", icon.getTierColor());

                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }, error -> {
            Toast.makeText(context, "Server Timeout!", Toast.LENGTH_LONG).show();

        });
        requestQueue.add(jsonObjectRequest);
        matchType = "competitive";
        getMatchInfo(getApplicationContext(), resultHeader.getName(), resultHeader.getTag(), matchType);
    }

    private void getMatchInfo(Context context, String riotName, String riotTag, String matchType) {
        ProgressDialog pd = new ProgressDialog(searchResultActivity.this);
        pd.setMessage("Loading Match info!");
        pd.show();
        APIUrl = "https://api.henrikdev.xyz/valorant/v3/matches/ap/";
        APIUrl_AccountInfo = APIUrl + riotName + "/" + riotTag + "?filter="+ matchType;
        //https://api.henrikdev.xyz/valorant/v3/matches/ap/Rezkcimhcs/SMZ01?filter=unrated
        //https://api.henrikdev.xyz/valorant/v3/matches/ap/Rezkcimhcs/SMZ01?filter=competitive
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl_AccountInfo, null,
                response -> {
                    try {
                        ArrayList<MatchHeaderDetails> entries = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        for (int count = 0; count < data.length(); count++) {
                            MatchHeaderDetails matchHeaderDetails = new MatchHeaderDetails();
                            JSONObject metadata = data.getJSONObject(count);
                            JSONObject metadata2 = metadata.getJSONObject("metadata");
                            matchHeaderDetails.setMap(metadata2.getString("map"));
                            matchHeaderDetails.setMatchID(metadata2.getString("matchid"));
                            Log.d("API Match Map: ", matchHeaderDetails.getMap());

                            String gameStartTime = convertTime(metadata2.getString("game_start_patched"));
                            matchHeaderDetails.setGame_start_patched(gameStartTime);
                            Log.d("API Match Map: ", matchHeaderDetails.getGame_start_patched());

                            matchHeaderDetails.setRounds_played(metadata2.getInt("rounds_played"));
                            Log.d("API Match Map: ", String.valueOf(matchHeaderDetails.getRounds_played()));

                            JSONObject players = metadata.getJSONObject("players");
                            JSONArray all_players = players.getJSONArray("all_players");

                            for (int i = 0; i < all_players.length(); i++) {
                                JSONObject player = all_players.getJSONObject(i);
                                String Name = player.getString("name");
                                matchHeaderDetails.setMatchTier(player.getString("currenttier"));
                                Log.d("API Match Name: ", Name + " " + riotName);
                                if (Name.equals(riotName)) {
                                    matchHeaderDetails.setCharacter(player.getString("character"));
                                    Log.d("API Match Character: ", matchHeaderDetails.getCharacter());
                                    arrMatchAgents[count] = matchHeaderDetails.getCharacter();

                                    matchHeaderDetails.setTeamSide(player.getString("team"));
                                    Log.d("API Match Team : ", matchHeaderDetails.getTeamSide());
                                    JSONObject stats = player.getJSONObject("stats");
                                    matchHeaderDetails.setScore(stats.getInt("score"));
                                    Log.d("API Match Score : ", String.valueOf(matchHeaderDetails.getScore()));
                                    matchHeaderDetails.setAcs(matchHeaderDetails.getScore() / matchHeaderDetails.getRounds_played());
                                    Log.d("API Match ACS : ", String.valueOf(matchHeaderDetails.getAcs()));

                                    matchHeaderDetails.setKills(stats.getInt("kills"));
                                    Log.d("API Match Kills : ", String.valueOf(matchHeaderDetails.getKills()));

                                    matchHeaderDetails.setDeaths(stats.getInt("deaths"));
                                    Log.d("API Match Deaths : ", String.valueOf(matchHeaderDetails.getDeaths()));

                                    matchHeaderDetails.setAssists(stats.getInt("assists"));
                                    Log.d("API Match Assists : ", String.valueOf(matchHeaderDetails.getAssists()));

                                    double kda = (matchHeaderDetails.getKills() + matchHeaderDetails.getAssists());
                                    matchHeaderDetails.setKdaRatio((double) Math.round((kda / matchHeaderDetails.getDeaths()) * 100) / 100);
                                    Log.d("API Match KDA% : ", String.valueOf(matchHeaderDetails.getKdaRatio()));
                                    double kdaTotal = resultHeader.getPlayerTotalKDRatio();
                                    kdaTotal = kdaTotal + matchHeaderDetails.getKdaRatio();
                                    resultHeader.setPlayerTotalKDRatio(kdaTotal);

                                    matchHeaderDetails.setHeadshots(stats.getInt("headshots"));
                                    Log.d("API Match Headshot : ", String.valueOf(matchHeaderDetails.getHeadshots()));
                                    matchHeaderDetails.setBodyshots(stats.getInt("bodyshots"));
                                    Log.d("API Match Bodyshot : ", String.valueOf(matchHeaderDetails.getBodyshots()));
                                    matchHeaderDetails.setLegshots(stats.getInt("legshots"));
                                    Log.d("API Match Legshot : ", String.valueOf(matchHeaderDetails.getLegshots()));
                                    double totalShot = matchHeaderDetails.getBodyshots() + matchHeaderDetails.getLegshots() + matchHeaderDetails.getHeadshots();
                                    double hs = matchHeaderDetails.getHeadshots() / totalShot;
                                    Log.d("API Match HS%1 : ", String.valueOf(hs));
                                    matchHeaderDetails.setHeadshotratio((double) Math.round((matchHeaderDetails.getHeadshots() / totalShot) * 100));
                                    Log.d("API Match HS% : ", String.valueOf(matchHeaderDetails.getHeadshotratio()));

                                    JSONObject teams = metadata.getJSONObject("teams");
                                    Log.d("API TEAMS WHOLE", String.valueOf(teams));
                                    JSONObject side2 = teams.getJSONObject(matchHeaderDetails.getTeamSide().toLowerCase());
                                    matchHeaderDetails.setHas_won(side2.getBoolean("has_won"));
                                    Log.d("API Match Won : ", String.valueOf(matchHeaderDetails.isHas_won()));
                                    if (matchHeaderDetails.isHas_won()) {
                                        int prev = resultHeader.getPlayerTotalWin();
                                        prev = prev + 1;
                                        resultHeader.setPlayerTotalWin(prev);
                                    }
                                    matchHeaderDetails.setRounds_won(side2.getInt("rounds_won"));
                                    Log.d("API Match rWon : ", String.valueOf(matchHeaderDetails.getRounds_won()));
                                    matchHeaderDetails.setRounds_lost(side2.getInt("rounds_lost"));
                                    Log.d("API Match rLose : ", String.valueOf(matchHeaderDetails.getRounds_lost()));

                                    matchHeaderDetails.setRrChanges(arrRRchanges[count]);
                                    Log.d("APImatchRR : ", String.valueOf(matchHeaderDetails.getRrChanges()));
                                    matchHeaderDetails.setAgentIcon(icon.getAgentIcon());
                                    matchHeaderDetails.setTierIcon(icon.getTierIcon());

                                    entries.add(matchHeaderDetails);
                                    break;

                                }
                            }
                            LinearLayoutManager manager = new LinearLayoutManager(searchResultActivity.this);
                            recyclerView.setLayoutManager(manager);
                            rvaMatch = new RVAMatch(searchResultActivity.this);
                            recyclerView.setAdapter(rvaMatch);
                            rvaMatch.setItem(entries);
                        }
                        //JSONObject player = all_players.getJSONObject(0);

                        setMatchDetails(getApplicationContext());
                        pd.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                },
                error -> {
                    Toast.makeText(context, "Server Timeout!", Toast.LENGTH_LONG).show();
                    onBackPressed();
                    pd.dismiss();
                });
        requestQueue.add(jsonObjectRequest);

    }

    private void setMatchDetails(Context context) {
        linearLayout.setVisibility(View.VISIBLE);
        if (arrMatchAgents[0].equalsIgnoreCase("Sova")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_sova)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Breach")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_breach)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Fade")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_fade)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Astra")) {
            Picasso.with(context)
                    .load(R.drawable.icon_astra)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Brimstone")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_brimstone)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Chamber")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_chamber)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Cypher")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_cypher)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Jett")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_jett)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("KAY/O")) {
            Picasso.with(context)
                    .load(R.drawable.portrait_kayo)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Killjoy")) {
            Picasso.with(context).load(R.drawable.portrait_killjoy)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Neon")) {
            Picasso.with(context).load(R.drawable.portrait_neon)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Omen")) {
            Picasso.with(context).load(R.drawable.portrait_omen)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Phoenix")) {
            Picasso.with(context).load(R.drawable.portrait_phoenix)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Raze")) {
            Picasso.with(context).load(R.drawable.portrait_raze)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Reyna")) {
            Picasso.with(context).load(R.drawable.portrait_reyna)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Sage")) {
            Picasso.with(context).load(R.drawable.portrait_sage)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Skye")) {
            Picasso.with(context).load(R.drawable.portrait_skye)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Viper")) {
            Picasso.with(context).load(R.drawable.portrait_viper)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);
        } else if (arrMatchAgents[0].equalsIgnoreCase("Yoru")) {
            Picasso.with(context).load(R.drawable.portrait_yoru)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imgCoverImage);

        }
        Picasso.with(searchResultActivity.this).load(resultHeader.getCardSmallUrl())
                .fit().centerInside()
                .placeholder(R.drawable.logo1)
                .into(imgProfileImage);
        Log.d("API", "name here" + resultHeader.getName());
        tvProfileLevel.setText(resultHeader.getLevel());
        tvProfileRiotName.setText(resultHeader.getName() + " #" + resultHeader.getTag());

        Picasso.with(searchResultActivity.this).load(icon.getTierIcon())
                .fit().centerInside()
                .placeholder(R.drawable.logo1)
                .into(imgTier);
        tvRankWithElo.setText(resultHeader.getCurrenttierpatched() + " - " + resultHeader.getRanking_in_tier());
        tvKDARatioandWinLose.setText(String.valueOf(df.format(resultHeader.getPlayerTotalKDRatio() / 5))
                + " KDA | " + resultHeader.getPlayerTotalWin() + "W  " + (5 - resultHeader.getPlayerTotalWin()) + "L");
        tvWinRatio.setText(String.valueOf(df.format((resultHeader.getPlayerTotalWin() / 5.00) * 100)) + "% Win Rate");

        getTopPlayed();
    }

    private void getTopPlayed() {
        totalGames = 0;
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(arrMatchAgents));
        String arrMatchAgentsUnique[] = linkedHashSet.toArray(new String[]{});
        arrMatchAgentsandPlayed = new String[arrMatchAgentsUnique.length][2];
        for (int x = 0; x < arrMatchAgentsUnique.length; ++x) {
            int counter = 0;
            int z = 1;
            for (int y = 0; y < arrMatchAgents.length; ++y) {
                if (arrMatchAgentsUnique[x].equals(arrMatchAgents[y])) {
                    counter++;
                }
                arrMatchAgentsandPlayed[x][0] = arrMatchAgentsUnique[x];
                arrMatchAgentsandPlayed[x][z] = String.valueOf(counter);
            }
        }
        for (int x = 0; x < arrMatchAgentsUnique.length; x++) {
            totalGames = totalGames + Integer.parseInt(arrMatchAgentsandPlayed[x][1]);
        }
        Arrays.sort(arrMatchAgentsandPlayed, new Comparator<String[]>() {
            public int compare(final String[] first, final String[] second) {
                return Double.valueOf(second[1]).compareTo(
                        Double.valueOf(first[1])
                );
            }
        });
        int arrayCount = arrMatchAgentsandPlayed.length - 1;
        if (!(arrayCount == -1)) {
            tvAgent1.setText(String.valueOf((Double.parseDouble(arrMatchAgentsandPlayed[0][1]) / 5) * 100.00) + "%");
            Log.d("TopAgent1", String.valueOf((Double.parseDouble(arrMatchAgentsandPlayed[0][1]) / 5) * 100.00) + "%");
            setIconTop1(getApplicationContext(), arrMatchAgentsandPlayed[0][0], imgAgent2);
            Log.d("TopAgentIcon1", arrMatchAgentsandPlayed[0][0]);
            arrayCount--;
        }
        if (!(arrayCount == -1)) {
            tvAgent2.setText(String.valueOf((Double.parseDouble(arrMatchAgentsandPlayed[1][1]) / 5) * 100.00) + "%");
            Log.d("TopAgent2", String.valueOf((Double.parseDouble(arrMatchAgentsandPlayed[1][1]) / 5) * 100.00) + "%");
            setIconTop1(getApplicationContext(), arrMatchAgentsandPlayed[1][0], imgAgent1);
            Log.d("TopAgentIcon2", arrMatchAgentsandPlayed[1][0]);
            arrayCount--;
        } else {
            imgAgent2.setVisibility(View.GONE);
            tvAgent2.setVisibility(View.GONE);
        }
        if (!(arrayCount == -1)) {
            tvAgent3.setText(String.valueOf((Double.parseDouble(arrMatchAgentsandPlayed[2][1]) / 5) * 100.00) + "%");
            Log.d("TopAgent3", String.valueOf((Double.parseDouble(arrMatchAgentsandPlayed[2][1]) / 5) * 100.00) + "%");
            setIconTop1(getApplicationContext(), arrMatchAgentsandPlayed[2][0], imgAgent3);
            Log.d("TopAgentIcon3", arrMatchAgentsandPlayed[2][0]);
            arrayCount--;
        } else {
            imgAgent3.setVisibility(View.GONE);
            tvAgent3.setVisibility(View.GONE);
        }
    }

    private void setIconTop1(Context context, String agentName, ImageView imageView) {
        if (agentName.equals("Sova")) {
            Picasso.with(context)
                    .load(R.drawable.icon_sova)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Breach")) {
            Picasso.with(context)
                    .load(R.drawable.icon_breach)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Brimstone")) {
            Picasso.with(context)
                    .load(R.drawable.icon_brimstone)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Chamber")) {
            Picasso.with(context)
                    .load(R.drawable.icon_chamber)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Fade")) {
            Picasso.with(context)
                    .load(R.drawable.icon_fade)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Cypher")) {
            Picasso.with(context)
                    .load(R.drawable.icon_cypher)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Jett")) {
            Picasso.with(context)
                    .load(R.drawable.icon_jett)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("KAY/O")) {
            Picasso.with(context)
                    .load(R.drawable.icon_kayo)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Astra")) {
            Picasso.with(context)
                    .load(R.drawable.icon_astra)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Killjoy")) {
            Picasso.with(context).load(R.drawable.icon_killjoy)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Neon")) {
            Picasso.with(context).load(R.drawable.icon_neon)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Omen")) {
            Picasso.with(context).load(R.drawable.icon_omen)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Phoenix")) {
            Picasso.with(context).load(R.drawable.icon_phoenix)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Raze")) {
            Picasso.with(context).load(R.drawable.icon_raze)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Reyna")) {
            Picasso.with(context).load(R.drawable.icon_reyna)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Sage")) {
            Picasso.with(context).load(R.drawable.icon_sage)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Skye")) {
            Picasso.with(context).load(R.drawable.icon_skye)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Viper")) {
            Picasso.with(context).load(R.drawable.icon_viper)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);
        } else if (agentName.equals("Yoru")) {
            Picasso.with(context).load(R.drawable.icon_yoru)
                    .placeholder(R.drawable.logo1)
                    .into(imageView);

        }
    }

    private void SplitRiotTag(String riotName) {
        String[] splitSearch = riotName.split("#");
        strName = splitSearch[0];
        strTag = splitSearch[1];
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        linearLayout.setVisibility(View.INVISIBLE);
//        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
//        sharedPreferences.edit().remove("searchKey").commit();
        finish();

    }

    private void getUtils() {
        linearLayout = findViewById(R.id.result_layProfile);

        imgProfileImage = findViewById(R.id.res_imgProfile);
        imgCoverImage = findViewById(R.id.result_imgCoverPhoto);

        tvProfileLevel = findViewById(R.id.res_tvLevel);
        tvProfileRiotName = findViewById(R.id.res_tvRiotUsername);
        tvWinRatio = findViewById(R.id.res_tvWinRatio);
        tvKDARatioandWinLose = findViewById(R.id.res_tvKDARatioandWinLose);

        //Rank Header
        imgTier = findViewById(R.id.res_imgTier);
        tvRankWithElo = findViewById(R.id.res_tvRankWithElo);

        //Match Header
        recyclerView = findViewById(R.id.matchRecyclerView);

        recyclerView.setHasFixedSize(true);

        tvAgent1 = findViewById(R.id.res_tvAgent1st);
        tvAgent2 = findViewById(R.id.res_tvAgent2nd);
        tvAgent3 = findViewById(R.id.res_tvAgent3rd);

        imgAgent1 = findViewById(R.id.res_imgAgent1st);
        imgAgent2 = findViewById(R.id.res_imgAgent2nd);
        imgAgent3 = findViewById(R.id.res_imgAgent3rd);

        fab = findViewById(R.id.res_SearchFab);
    }

    private String convertTime(String rawTime) {
        String time = "";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMMM d,yyyy hh:mm aaa");
            format1.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm aaa");
            format2.setTimeZone(TimeZone.getDefault());

            Date past = format1.parse(rawTime);
            time = (format2.format(past));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}