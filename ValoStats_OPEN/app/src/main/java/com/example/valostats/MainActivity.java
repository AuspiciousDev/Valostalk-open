package com.example.valostats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.valostats.DAO.DAOLinkedAccount;
import com.example.valostats.DAO.DAOUser;
import com.example.valostats.Model.LinkedAccount;
import com.example.valostats.Model.User;
import com.example.valostats.Model.tempLinkedAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edtSearch;
    private Button btnSearch, btnLink;
    private String strSearch, strName, strTag, loginUserName;
    private String MainURL = "https://api.henrikdev.xyz/valorant/v1/";
    private String AccountInfoURL;
    private Intent intent;
    private DAOLinkedAccount daoLinkedAccount;
    private LinkedAccount linkedAccount;

    private ImageView linkImgProfile;
    private TextView linkRiotIGN, linkAccLevel;
    private LinearLayoutCompat linearLayoutCompat;
    private CardView cardView;

    private String MyPref = "linkedAccount";
    private String nameKey;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharePrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSearch = findViewById(R.id.main_edtSearch);
        btnSearch = findViewById(R.id.main_btnSearch);
        btnLink = findViewById(R.id.main_edtLinkProfile);

        linkImgProfile = findViewById(R.id.main_imgProfile);
        linkRiotIGN = findViewById(R.id.main_tvName);
        linkAccLevel = findViewById(R.id.main_tvLevel);
        linearLayoutCompat = findViewById(R.id.main_existProfile);
        cardView = findViewById(R.id.main_cvProfile);


        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        sharePrefEdit = sharedPreferences.edit();
        checkLinkedAccount();
        btnSearch.setOnClickListener(view -> {
            strSearch = edtSearch.getText().toString();
            if (validate()) {
                strSearch.replaceAll(" ", "%20");
                sharePrefEdit.putString("searchKey", strSearch);
                sharePrefEdit.commit();
               startActivity(new Intent(MainActivity.this, searchResultActivity.class));
            }
        });
        btnLink.setOnClickListener(view -> {
            strSearch = edtSearch.getText().toString();
            SplitRiotTag(strSearch);
            callAPI(getApplicationContext(), strName, strTag);

        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Unlink Account");
                builder1.setMessage("Are you use to unlink the account?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               sharedPreferences.edit().clear().commit();
                                //settings.edit().remove("KeyName").commit();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });
        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(this, searchResultActivity.class);
            intent.putExtra("riotUsername", linkedAccount.getName() + "#" + linkedAccount.getTag());
            startActivity(intent);
            
        });
    }

    public void callAPI(Context context, String name, String tag) {
        linkedAccount = new LinkedAccount();
        AccountInfoURL = MainURL + "account/" + name + "/" + tag;
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Searching");
        pd.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AccountInfoURL, null,
                response -> {
                    try {
                        // JSONObject jsonReturn = new JSONObject(JSONReponse);

                        String ApiResponse = response.getString("status");
                        Log.d("ApiRes1URL : ", AccountInfoURL);
                        Log.d("ApiRes1 : ", ApiResponse);
                        JSONObject jsonData = (JSONObject) response.get("data");
                        JSONObject card = (JSONObject) jsonData.get("card");

                        linkedAccount.setName(jsonData.getString("name"));
                        Log.d("callAPI ", linkedAccount.getName());
                        linkedAccount.setTag(jsonData.getString("tag"));
                        linkedAccount.setRegion(jsonData.getString("region"));
                        linkedAccount.setPuuid(jsonData.getString("puuid"));
                        linkedAccount.setLevel(jsonData.getString("account_level"));
                        linkedAccount.setCard(card.getString("small"));


                        sharePrefEdit = sharedPreferences.edit();
                        sharePrefEdit.putString("nameKey", linkedAccount.getName());
                        sharePrefEdit.putString("tagKey", linkedAccount.getTag());
                        sharePrefEdit.putString("regionKey", linkedAccount.getRegion());
                        sharePrefEdit.putString("puuidKey", linkedAccount.getPuuid());
                        sharePrefEdit.putString("accLeveKey", linkedAccount.getLevel());
                        sharePrefEdit.putString("cardKey", linkedAccount.getCard());
                        sharePrefEdit.commit();
                        Snackbar.make(findViewById(android.R.id.content), "Account Linked!", Snackbar.LENGTH_SHORT).show();

                        pd.dismiss();
                        // setLinkedAccount();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }, error -> {
            Intent intent = new Intent(this, UserNotFound.class);
            intent.putExtra("riotUsername", strSearch);
            startActivity(intent);
//                new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                try {
//                    String responseBody = new String(volleyError.networkResponse.data, "utf-8");
//                    JSONObject jsonObject = new JSONObject(responseBody);
//                    String error = jsonObject.getString("status");
//                    //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
//
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                    builder1.setTitle("404");
//                    builder1.setMessage("WE COULD NOT FIND THE PLAYER\n" + name + " #" + tag);
//                    builder1.setCancelable(true);
//
//                    builder1.setPositiveButton(
//                            "Go Back",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
//                } catch (JSONException e) {
//                    //Handle a malformed json response
//                } catch (UnsupportedEncodingException error) {
//

            pd.dismiss();

        });
        requestQueue.add(jsonObjectRequest);

    }

    private void SplitRiotTag(String riotName) {
        String[] splitSearch = riotName.split("#");
        strName = splitSearch[0];
        strName.replaceAll(" ", "%20");
        strTag = splitSearch[1];
    }

    private void checkLinkedAccount() {

        nameKey = sharedPreferences.getString("nameKey", "");
        if (!(nameKey.equals(null)) && !(nameKey.equals(""))) {
            linkedAccount = new LinkedAccount();

            btnLink.setVisibility(View.GONE);

            linkedAccount.setName(sharedPreferences.getString("nameKey", ""));
            linkedAccount.setTag(sharedPreferences.getString("tagKey", ""));
            linkedAccount.setRegion(sharedPreferences.getString("regionKey", ""));
            linkedAccount.setLevel(sharedPreferences.getString("accLeveKey", ""));
            linkedAccount.setCard(sharedPreferences.getString("cardKey", ""));

            Picasso.with(getApplicationContext()).load(linkedAccount.getCard())
                    .fit().centerInside()
                    .placeholder(R.drawable.icon_unknown)
                    .into(linkImgProfile);
            linkRiotIGN.setText(linkedAccount.getName() + " #" + linkedAccount.getTag());
            linkAccLevel.setText("Level " + linkedAccount.getLevel());

            linearLayoutCompat.setVisibility(View.VISIBLE);

        } else {

            btnLink.setVisibility(View.VISIBLE);
            linearLayoutCompat.setVisibility(View.GONE);
        }
    }

    private boolean validate() {
        Boolean bool = false;
        if (strSearch.isEmpty()) {
            edtSearch.setError("Empty Field!");
            bool = false;
        } else if (!(strSearch.contains("#"))) {
            edtSearch.setError("Invalid Riot ID!");
            bool = false;
        } else {
            bool = true;
        }
        return bool;
    }

    private void setLinkedAccount() {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        Log.d("setLinked ", linkedAccount.getName());
        daoLinkedAccount = new DAOLinkedAccount();
        linkedAccount.setUsername(loginUserName);
        daoLinkedAccount.add(linkedAccount).addOnSuccessListener(suc -> {
            Snackbar.make(findViewById(android.R.id.content), "Account Linked!", Snackbar.LENGTH_SHORT).show();
            pd.dismiss();
        }).addOnFailureListener(er -> {
            Snackbar.make(findViewById(android.R.id.content), er.toString(), Snackbar.LENGTH_SHORT).show();
            pd.dismiss();
        });

    }
}