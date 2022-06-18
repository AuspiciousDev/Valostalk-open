package com.example.valostats.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valostats.MatchDetailsActivity;
import com.example.valostats.Model.MatchHeaderDetails;
import com.example.valostats.Model.ResultHeader;
import com.example.valostats.R;
import com.example.valostats.searchResultActivity;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RVAMatch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    // ArrayList<ResultHeader> resultList = new ArrayList<>();
    ArrayList<MatchHeaderDetails> matchDetailsList = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public RVAMatch(Context context) {
        this.context = context;
    }

    public void setItem(ArrayList<MatchHeaderDetails> matchHeaderDetails) {
        matchDetailsList.addAll(matchHeaderDetails);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_match_list, parent, false);
        return new ViewHolderMatch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderMatch viewHolderMatch = (ViewHolderMatch) holder;
        MatchHeaderDetails matchHeaderDetails = matchDetailsList.get(position);
        if (Integer.parseInt(matchHeaderDetails.getRrChanges()) < 0) {
            viewHolderMatch.tvMatchMMR.setTextColor(ContextCompat.getColor(context, R.color.lightRed));
            viewHolderMatch.tvMatchMMR.setText(String.valueOf(matchHeaderDetails.getRrChanges()));
        }
        if (Integer.parseInt(matchHeaderDetails.getRrChanges()) > 0) {
            viewHolderMatch.tvMatchMMR.setTextColor(ContextCompat.getColor(context, R.color.lightGreen));
            viewHolderMatch.tvMatchMMR.setText("+" + String.valueOf(matchHeaderDetails.getRrChanges()));
        }

        viewHolderMatch.tvMatchMap.setText(matchHeaderDetails.getMap());
        viewHolderMatch.tvMatchRoundWon.setText(String.valueOf(matchHeaderDetails.getRounds_won()));
        viewHolderMatch.tvMatchRoundLose.setText(String.valueOf(matchHeaderDetails.getRounds_lost()));
        viewHolderMatch.tvMatchTime.setText(String.valueOf(matchHeaderDetails.getGame_start_patched()));
        //viewHolderMatch.tvMatchPerformanceRank.setText(mat.getRanking_in_tier());
        viewHolderMatch.tvMatchKDRatio.setText(String.valueOf(df.format(matchHeaderDetails.getKdaRatio()) + " KD"));
        viewHolderMatch.tvMatchKDA.setText(String.valueOf(matchHeaderDetails.getKills() + " / " + matchHeaderDetails.getDeaths() + " / " + matchHeaderDetails.getAssists()));
        viewHolderMatch.tvMatchHeadshotRatio.setText(String.valueOf(matchHeaderDetails.getHeadshotratio() + " HS%"));
        viewHolderMatch.tvMatchACS.setText((String.valueOf(matchHeaderDetails.getScore() / matchHeaderDetails.getRounds_played()) + " ACS"));
        if (matchHeaderDetails.isHas_won()) {
            viewHolderMatch.view.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGreen));
            viewHolderMatch.layoutCompat.setBackground(ContextCompat.getDrawable(context, R.drawable.matchfill_win));

        } else {
            viewHolderMatch.view.setBackgroundColor(ContextCompat.getColor(context, R.color.lightRed));
            viewHolderMatch.layoutCompat.setBackground(ContextCompat.getDrawable(context, R.drawable.matchfill_lose));

        }
        Picasso.with(context).load(matchHeaderDetails.getTierIcon())
                .fit().centerInside()
                .placeholder(R.drawable.logo1)
                .into(viewHolderMatch.imgMatchRank);


        if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Astra")) {
            Picasso.with(context)
                    .load(R.drawable.icon_astra)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Breach")) {
            Picasso.with(context)
                    .load(R.drawable.icon_breach)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Brimstone")) {
            Picasso.with(context)
                    .load(R.drawable.icon_brimstone)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Fade")) {
            Picasso.with(context)
                    .load(R.drawable.icon_fade)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Chamber")) {
            Picasso.with(context)
                    .load(R.drawable.icon_chamber)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Cypher")) {
            Picasso.with(context)
                    .load(R.drawable.icon_cypher)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Jett")) {
            Picasso.with(context)
                    .load(R.drawable.icon_jett)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("KAY/O")) {
            Picasso.with(context)
                    .load(R.drawable.icon_kayo)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Killjoy")) {
            Picasso.with(context).load(R.drawable.icon_killjoy)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Neon")) {
            Picasso.with(context).load(R.drawable.icon_neon)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Omen")) {
            Picasso.with(context).load(R.drawable.icon_omen)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Phoenix")) {
            Picasso.with(context).load(R.drawable.icon_phoenix)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Raze")) {
            Picasso.with(context).load(R.drawable.icon_raze)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Reyna")) {
            Picasso.with(context).load(R.drawable.icon_reyna)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Sage")) {
            Picasso.with(context).load(R.drawable.icon_sage)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Skye")) {
            Picasso.with(context).load(R.drawable.icon_skye)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Sova")) {
            Picasso.with(context).load(R.drawable.icon_sova)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Viper")) {
            Picasso.with(context).load(R.drawable.icon_viper)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        } else if (matchHeaderDetails.getCharacter().equalsIgnoreCase("Yoru")) {
            Picasso.with(context).load(R.drawable.icon_yoru)
                    .placeholder(R.drawable.logo1)
                    .into(viewHolderMatch.imgMatchAgent);
        }

        viewHolderMatch.layoutCompat.setOnClickListener(view -> {
            Intent intent = new Intent(context, MatchDetailsActivity.class);
            intent.putExtra("editEntry", matchHeaderDetails);
            context.startActivity(intent);
        });
        setMapHeader(context, matchHeaderDetails.getMap(), viewHolderMatch.imgMatchMap);
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

    @Override
    public int getItemCount() {
        return matchDetailsList.size();
    }
}
