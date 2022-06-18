package com.example.valostats.Adapter;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valostats.R;

public class ViewHolderMatch extends RecyclerView.ViewHolder {
    public ImageView imgMatchRank, imgMatchAgent, imgMatchMap;
    public TextView tvMatchMMR, tvMatchMap, tvMatchTime, tvMatchRoundWon,
            tvMatchRoundLose, tvMatchPerformanceRank, tvMatchKDRatio, tvMatchKDA, tvMatchHeadshotRatio, tvMatchACS;
    public LinearLayoutCompat layoutCompat;
    public LinearLayout view;
    public SharedPreferences viewSharedPreferences;
    public  SharedPreferences.Editor viewSharePrefEdit;
    public String viewMyPref = "linkedAccount";
    public ViewHolderMatch(@NonNull View itemView) {
        super(itemView);
        imgMatchRank = itemView.findViewById(R.id.matchImgTier);
        tvMatchMMR = itemView.findViewById(R.id.matchTvMMR);
        imgMatchAgent = itemView.findViewById(R.id.matchAgent);
        imgMatchMap = itemView.findViewById(R.id.match_imgMap);
        tvMatchMap = itemView.findViewById(R.id.matchMap);
        tvMatchRoundWon = itemView.findViewById(R.id.matchRoundWon);
        tvMatchRoundLose = itemView.findViewById(R.id.matchRoundLose);
        tvMatchTime = itemView.findViewById(R.id.matchTime);
        tvMatchPerformanceRank = itemView.findViewById(R.id.matchPerformanceRank);
        tvMatchKDA = itemView.findViewById(R.id.matchKDA);
        tvMatchKDRatio = itemView.findViewById(R.id.matchKDARatio);
        tvMatchHeadshotRatio = itemView.findViewById(R.id.matchHeadshotRatio);
        tvMatchACS = itemView.findViewById(R.id.matchACS);
        view = itemView.findViewById(R.id.recView);
        layoutCompat = itemView.findViewById(R.id.reclayout);



    }
}
