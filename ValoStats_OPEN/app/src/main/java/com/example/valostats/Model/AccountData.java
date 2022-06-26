package com.example.valostats.Model;

public class AccountData {
    private String puuid;
    private String name;
    private String tag;
    private String cardSmallUrl;
    private String level;
    private String region;


    public AccountData() {

    }

    public AccountData(String puuid, String name, String tag, String cardSmallUrl, String level, String region) {
        this.puuid = puuid;
        this.name = name;
        this.tag = tag;
        this.cardSmallUrl = cardSmallUrl;
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
