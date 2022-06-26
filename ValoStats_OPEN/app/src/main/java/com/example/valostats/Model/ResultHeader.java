package com.example.valostats.Model;

public class ResultHeader {
    private String puuid;
    private String name;
    private String tag;
    private String cardSmallUrl;
    private String level;
    private String region;
    private String currenttierpatched;
    private int currenttier;
    private int ranking_in_tier;
    private int Mmr_change_to_last_game;
    private int PlayerTotalWin;
    private double PlayerTotalKDRatio;

    private double totalHeadshotratio;
    public ResultHeader() {

    }
    public ResultHeader(double totalHeadshotratio) {
        this.totalHeadshotratio = totalHeadshotratio;
    }

    public double getTotalHeadshotratio() {
        return totalHeadshotratio;
    }

    public void setTotalHeadshotratio(double totalHeadshotratio) {
        this.totalHeadshotratio = totalHeadshotratio;
    }
    public ResultHeader(String puuid, String name, String tag, String cardSmallUrl, int currenttier, String currenttierpatched, int ranking_in_tier, int mmr_change_to_last_game, int playerTotalWin, double playerTotalKDRatio, String level, String region) {
        this.puuid = puuid;
        this.name = name;
        this.tag = tag;
        this.cardSmallUrl = cardSmallUrl;
        this.currenttier = currenttier;
        this.currenttierpatched = currenttierpatched;
        this.ranking_in_tier = ranking_in_tier;
        Mmr_change_to_last_game = mmr_change_to_last_game;
        PlayerTotalWin = playerTotalWin;
        PlayerTotalKDRatio = playerTotalKDRatio;
        this.level = level;
        this.region = region;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCardSmallUrl() {
        return cardSmallUrl;
    }

    public void setCardSmallUrl(String cardSmallUrl) {
        this.cardSmallUrl = cardSmallUrl;
    }

    public int getCurrenttier() {
        return currenttier;
    }

    public void setCurrenttier(int currenttier) {
        this.currenttier = currenttier;
    }

    public String getCurrenttierpatched() {
        return currenttierpatched;
    }

    public void setCurrenttierpatched(String currenttierpatched) {
        this.currenttierpatched = currenttierpatched;
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

    public int getPlayerTotalWin() {
        return PlayerTotalWin;
    }

    public void setPlayerTotalWin(int playerTotalWin) {
        PlayerTotalWin = playerTotalWin;
    }

    public double getPlayerTotalKDRatio() {
        return PlayerTotalKDRatio;
    }

    public void setPlayerTotalKDRatio(double playerTotalKDRatio) {
        PlayerTotalKDRatio = playerTotalKDRatio;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
