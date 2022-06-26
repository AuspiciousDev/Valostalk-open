package com.example.valostats.DAO;

import com.example.valostats.Model.AccountData;
import com.example.valostats.Model.AccountRank;
import com.example.valostats.Model.ResultHeader;
import com.example.valostats.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOFriendsLeaderBoard {
    private DatabaseReference databaseReference;

    public DAOFriendsLeaderBoard() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Friends");
    }

    public Task<Void> addAccountData(AccountData accountData) {
        return databaseReference.child(accountData.getPuuid()).setValue(accountData);
    }
    public Task<Void> addAccountRank(AccountRank accountRank, String PUUID) {
        return databaseReference.child(PUUID).child("rank").setValue(accountRank);
    }
    public Task<Void> deleteAccount(String PUUID) {
        return databaseReference.child(PUUID).removeValue();
    }

}
