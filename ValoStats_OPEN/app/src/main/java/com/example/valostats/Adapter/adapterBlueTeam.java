package com.example.valostats.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valostats.MatchDetailsActivity;
import com.example.valostats.Model.MatchHeaderDetails;
import com.example.valostats.R;
import com.example.valostats.searchResultActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapterBlueTeam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    // ArrayList<ResultHeader> resultList = new ArrayList<>();
    ArrayList<MatchHeaderDetails> matchDetailsList = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public adapterBlueTeam(Context context) {
        this.context = context;
    }

    public void setItem(ArrayList<MatchHeaderDetails> matchHeaderDetails) {
        matchDetailsList.addAll(matchHeaderDetails);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_blue_team_list, parent, false);
        return new ViewHolderBlueTeam(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderBlueTeam viewh = (ViewHolderBlueTeam) holder;
        MatchHeaderDetails details = matchDetailsList.get(position);
        viewh.tvMatchIGNName.setText(details.getRiotName());
        viewh.tvMatchIGNTag.setText("#" + details.getRiotTag());

        viewh.tvMatchKDA.setText(details.getKills() + " / " + details.getDeaths() + " / " + details.getAssists());
        viewh.tvMatchACS.setText(String.valueOf(details.getAcs()).replace(".0", "") + " ACS");
        viewh.tvMatchADR.setText(details.getAdr() + " ADR");
        viewh.tvMatchKDRatio.setText(String.valueOf(df.format(details.getKdaRatio()) + " KD"));
        viewh.tvMatchHeadshotRatio.setText(String.valueOf(details.getHeadshotratio()).replace(".0", "") + " HS%");
        Picasso.with(context).load(details.getAgentIcon())
                .fit().centerInside()
                .placeholder(R.drawable.icon_unknown)
                .into(viewh.imgMatchAgent);
        Picasso.with(context).load(details.getTierIcon())
                .fit().centerInside()
                .placeholder(R.drawable.icon_unknown)
                .into(viewh.imgMatchRank);


        if (details.getMatchTier().equals("0")) {
            Picasso.with(context)
                    .load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("1")) {
            Picasso.with(context)
                    .load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("2")) {
            Picasso.with(context)
                    .load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        }else if (details.getMatchTier().equals("3")) {
            Picasso.with(context)
                    .load(R.drawable.icon_i1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("4")) {
            Picasso.with(context)
                    .load(R.drawable.icon_i2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("5")) {
            Picasso.with(context)
                    .load(R.drawable.icon_i3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("6")) {
            Picasso.with(context)
                    .load(R.drawable.icon_b1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("7")) {
            Picasso.with(context)
                    .load(R.drawable.icon_b2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("8")) {
            Picasso.with(context).load(R.drawable.icon_b3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("9")) {
            Picasso.with(context).load(R.drawable.icon_s1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("10")) {
            Picasso.with(context).load(R.drawable.icon_s2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        }else if (details.getMatchTier().equals("11")) {
            Picasso.with(context).load(R.drawable.icon_s3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("12")) {
            Picasso.with(context).load(R.drawable.icon_g1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("13")) {
            Picasso.with(context).load(R.drawable.icon_g2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("14")) {
            Picasso.with(context).load(R.drawable.icon_g3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("15")) {
            Picasso.with(context).load(R.drawable.icon_p1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("16")) {
            Picasso.with(context).load(R.drawable.icon_p2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("17")) {
            Picasso.with(context).load(R.drawable.icon_p3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("18")) {
            Picasso.with(context).load(R.drawable.icon_d1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("19")) {
            Picasso.with(context).load(R.drawable.icon_d2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        }else if (details.getMatchTier().equals("20")) {
            Picasso.with(context).load(R.drawable.icon_d3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        }else if (details.getMatchTier().equals("21")) {
            Picasso.with(context).load(R.drawable.icon_asc1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("22")) {
            Picasso.with(context).load(R.drawable.icon_asc2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("23")) {
            Picasso.with(context).load(R.drawable.icon_asc3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("24")) {
            Picasso.with(context).load(R.drawable.icon_im1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("25")) {
            Picasso.with(context).load(R.drawable.icon_im2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("26")) {
            Picasso.with(context).load(R.drawable.icon_im3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        } else if (details.getMatchTier().equals("27")) {
            Picasso.with(context).load(R.drawable.icon_rad)
                    .placeholder(R.drawable.icon_unknown)
                    .into(viewh.imgMatchRank);
        }else{
            Picasso.with(context).load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.logo1)
                    .into(viewh.imgMatchRank);
        }
        viewh.layoutCompat.setOnClickListener(view ->{
            Intent intent = new Intent(context, searchResultActivity.class);
            intent.putExtra("riotUsername", details.getRiotName() + "#" + details.getRiotTag());
            context. startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return matchDetailsList.size();
    }
}
