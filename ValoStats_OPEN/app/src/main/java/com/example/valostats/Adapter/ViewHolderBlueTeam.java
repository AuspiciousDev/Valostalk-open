package com.example.valostats.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valostats.R;

public class ViewHolderBlueTeam extends RecyclerView.ViewHolder {
    public ImageView imgMatchRank, imgMatchAgent;

    public TextView tvMatchIGNName,tvMatchIGNTag,tvMatchADR, tvMatchKDRatio, tvMatchKDA, tvMatchHeadshotRatio, tvMatchACS;
    public LinearLayoutCompat layoutCompat;
    public LinearLayout view;

    public ViewHolderBlueTeam(@NonNull View itemView) {
        super(itemView);
        imgMatchAgent = itemView.findViewById(R.id.matchAgent);
        imgMatchRank = itemView.findViewById(R.id.matchRank);
        tvMatchIGNName = itemView.findViewById(R.id.matchIGNname);
        tvMatchIGNTag = itemView.findViewById(R.id.matchIGNtag);
        tvMatchACS = itemView.findViewById(R.id.matchACS);
        tvMatchADR = itemView.findViewById(R.id.matchADR);
        tvMatchKDRatio = itemView.findViewById(R.id.matchKDARatio);
        tvMatchKDA = itemView.findViewById(R.id.matchKDA);
        tvMatchHeadshotRatio = itemView.findViewById(R.id.matchHsRatio);
        layoutCompat = itemView.findViewById(R.id.reclayout);
    }
}
