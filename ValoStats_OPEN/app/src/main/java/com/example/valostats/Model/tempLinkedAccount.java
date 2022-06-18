package com.example.valostats.Model;

public class tempLinkedAccount {
    private String name;
    private String tag;
    private String username;
    private String region;
    private String puuid;

    public tempLinkedAccount() {

    }

    public tempLinkedAccount(String name, String tag, String username, String region, String card, String puuid) {
        this.name = name;
        this.tag = tag;
        this.username = username;
        this.region = region;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
}
