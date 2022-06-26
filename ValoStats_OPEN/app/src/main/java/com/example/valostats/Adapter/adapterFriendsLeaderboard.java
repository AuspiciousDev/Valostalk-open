package com.example.valostats.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valostats.DAO.DAOFriendsLeaderBoard;
import com.example.valostats.MatchDetailsActivity;
import com.example.valostats.Model.MatchHeaderDetails;
import com.example.valostats.Model.ResultHeader;
import com.example.valostats.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class adapterFriendsLeaderboard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DAOFriendsLeaderBoard daoFriendsLeaderBoard;

    ArrayList<ResultHeader> resultList = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public adapterFriendsLeaderboard(Context context) {
        this.context = context;
    }

    public void setItem(ArrayList<ResultHeader> resultHeaders) {
        resultList.addAll(resultHeaders);
    }

    List adapterFriendsLeaderboard;

    public adapterFriendsLeaderboard(List adapterFriendsLeaderboard) {
        this.adapterFriendsLeaderboard = adapterFriendsLeaderboard;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_friends_leaderboard, parent, false);
        return new ViewHolderFriendsLeaderboard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderFriendsLeaderboard viewHolder = (ViewHolderFriendsLeaderboard) holder;
        ResultHeader resultHeader = resultList.get(position);

        Picasso.with(context)
                .load(resultHeader.getCardSmallUrl())
                .placeholder(R.drawable.icon_unknown)
                .into(viewHolder.imgLeaderboardIcon);
        viewHolder.tvLeaderboardName.setText(resultHeader.getName());
        viewHolder.tvLeaderboardTag.setText("#" + resultHeader.getTag());
        Log.d("Adapter", "name " + resultHeader.getName());

        setRankIcon(resultHeader.getCurrenttier(), viewHolder.imgLeaderboardRank);
        viewHolder.tvLeaderboardRank.setText(resultHeader.getCurrenttierpatched());
        viewHolder.tvLeaderboardRR.setText(String.valueOf(resultHeader.getRanking_in_tier()));

        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                daoFriendsLeaderBoard = new DAOFriendsLeaderBoard();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Remove Profile?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                daoFriendsLeaderBoard.deleteAccount(resultHeader.getPuuid())
                                        .addOnSuccessListener(suc -> {
                                            Toast.makeText(context, "Profile Removed!", Toast.LENGTH_SHORT).show();
                                        }).addOnFailureListener(er -> {
                                            Toast.makeText(context, "Error Removing Profile!", Toast.LENGTH_SHORT).show();
                                        });
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
    }

    private void setRankIcon(int currentTier, ImageView imageView) {
        if (currentTier == 0) {
            Picasso.with(context)
                    .load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 1) {
            Picasso.with(context)
                    .load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 2) {
            Picasso.with(context)
                    .load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 3) {
            Picasso.with(context)
                    .load(R.drawable.icon_i1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 4) {
            Picasso.with(context)
                    .load(R.drawable.icon_i2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 5) {
            Picasso.with(context)
                    .load(R.drawable.icon_i3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 6) {
            Picasso.with(context)
                    .load(R.drawable.icon_b1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 7) {
            Picasso.with(context)
                    .load(R.drawable.icon_b2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 8) {
            Picasso.with(context).load(R.drawable.icon_b3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 9) {
            Picasso.with(context).load(R.drawable.icon_s1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 10) {
            Picasso.with(context).load(R.drawable.icon_s2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 11) {
            Picasso.with(context).load(R.drawable.icon_s3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 12) {
            Picasso.with(context).load(R.drawable.icon_g1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 13) {
            Picasso.with(context).load(R.drawable.icon_g2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 14) {
            Picasso.with(context).load(R.drawable.icon_g3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 15) {
            Picasso.with(context).load(R.drawable.icon_p1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 16) {
            Picasso.with(context).load(R.drawable.icon_p2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 17) {
            Picasso.with(context).load(R.drawable.icon_p3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 18) {
            Picasso.with(context).load(R.drawable.icon_d1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 19) {
            Picasso.with(context).load(R.drawable.icon_d2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 20) {
            Picasso.with(context).load(R.drawable.icon_d3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 21) {
            Picasso.with(context).load(R.drawable.icon_asc1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 22) {
            Picasso.with(context).load(R.drawable.icon_asc1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 23) {
            Picasso.with(context).load(R.drawable.icon_asc3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 24) {
            Picasso.with(context).load(R.drawable.icon_im1)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 25) {
            Picasso.with(context).load(R.drawable.icon_im2)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 26) {
            Picasso.with(context).load(R.drawable.icon_im3)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else if (currentTier == 27) {
            Picasso.with(context).load(R.drawable.icon_rad)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        } else {
            Picasso.with(context).load(R.drawable.icon_unknown)
                    .placeholder(R.drawable.icon_unknown)
                    .into(imageView);
        }

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}
