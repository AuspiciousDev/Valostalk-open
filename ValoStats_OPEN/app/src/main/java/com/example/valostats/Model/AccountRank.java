package com.example.valostats.Model;

public class AccountRank {
    private String currenttierpatched;
    private int currenttier;
    private int ranking_in_tier;
    private int Mmr_change_to_last_game;

    public AccountRank() {

    }

    public AccountRank(String currenttierpatched, int currenttier, int ranking_in_tier, int mmr_change_to_last_game) {
        this.currenttierpatched = currenttierpatched;
        this.currenttier = currenttier;
        this.ranking_in_tier = ranking_in_tier;
        Mmr_change_to_last_game = mmr_change_to_last_game;
    }

    public String getCurrenttierpatched() {
        return currenttierpatched;
    }

    public void setCurrenttierpatched(String currenttierpatched) {
        this.currenttierpatched = currenttierpatched;
    }

    public int getCurrenttier() {
        return currenttier;
    }

    public void setCurrenttier(int currenttier) {
        this.currenttier = currenttier;
    }

    public int getRanking_in_tier() {
        return ranking_in_tier;
    }

    public void setRanking_in_tier(int ranking_in_tier) {
        this.ranking_in_tier = ranking_in_tier;
    }

    public int getMmr_change_to_last_game() {
        return Mmr_change_to_last_game;
    }

    public void setMmr_change_to_last_game(int mmr_change_to_last_game) {
        Mmr_change_to_last_game = mmr_change_to_last_game;
    }
}
