package com.example.valostats.Adapter;

import android.content.SharedPreferences;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valostats.R;

import org.w3c.dom.Text;

public class ViewHolderFriendsLeaderboard extends RecyclerView.ViewHolder {
    public ImageView imgLeaderboardIcon, imgLeaderboardRank;
    public TextView tvLeaderboardName, tvLeaderboardLevel, tvLeaderboardTag,
            tvLeaderboardRank, tvLeaderboardRR;
    public LinearLayoutCompat cardView;

    public ViewHolderFriendsLeaderboard(@NonNull View itemView) {
        super(itemView);
        imgLeaderboardIcon = itemView.findViewById(R.id.leaderboard_imgProfile);
        imgLeaderboardRank = itemView.findViewById(R.id.leaderboard_imgRank);

        tvLeaderboardName = itemView.findViewById(R.id.leaderboard_tvName);
        tvLeaderboardTag = itemView.findViewById(R.id.leaderboard_tvTag);
        tvLeaderboardRank = itemView.findViewById(R.id.leaderboard_tvRank);
        tvLeaderboardRR = itemView.findViewById(R.id.leaderboard_tvRR);

        cardView = itemView.findViewById(R.id.leaderboard_lyLeaderboards);
    }
}
