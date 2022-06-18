package com.example.valostats.DAO;

import com.example.valostats.Model.LinkedAccount;
import com.example.valostats.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOLinkedAccount {
    private DatabaseReference databaseReference;

    public DAOLinkedAccount() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(LinkedAccount.class.getSimpleName());
    }
    public Task<Void> add(LinkedAccount linkedAccount) {
        return databaseReference.child(linkedAccount.getUsername()).setValue(linkedAccount);
    }
}
