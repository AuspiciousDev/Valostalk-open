package com.example.valostats.Model;

import java.io.Serializable;

public class MatchHeaderDetails implements Serializable {
    private int mmr_change_to_last_game;
    private String riotName;
    private String riotTag;
    private String character;
    private String characterIcon;
    private String map;
    private String game_start_patched;
    private String teamSide;
    private boolean has_won;
    private int rounds_played;
    private int rounds_won;
    private int rounds_lost;
    private double kdaRatio;
    private double kdRatio;
    private int kills;
    private int deaths;
    private int assists;
    private double headshotratio;

    private int headshots;
    private int bodyshots;
    private int legshots;
    private int score;
    private double acs;
    private String tierIcon;
    private String agentIcon;
    private String tierColor;
    private int MMR;
    private int damageMade;
    private int adr;
    private String matchTier;
    private String rrChanges;
    private String matchID;

    public MatchHeaderDetails() {

    }



    public MatchHeaderDetails(int mmr_change_to_last_game, String riotName, String riotTag, String character, String characterIcon, String map, String game_start_patched, String teamSide, boolean has_won, int rounds_played, int rounds_won, int rounds_lost, double kdaRatio, double kdRatio, int kills, int deaths, int assists, double headshotratio, int headshots, int bodyshots, int legshots, int score, double acs, String tierIcon, String agentIcon, String tierColor, int MMR, int damageMade, int adr, String matchTier, String rrChanges, String matchID) {
        this.mmr_change_to_last_game = mmr_change_to_last_game;
        this.riotName = riotName;
        this.riotTag = riotTag;
        this.character = character;
        this.characterIcon = characterIcon;
        this.map = map;
        this.game_start_patched = game_start_patched;
        this.teamSide = teamSide;
        this.has_won = has_won;
        this.rounds_played = rounds_played;
        this.rounds_won = rounds_won;
        this.rounds_lost = rounds_lost;
        this.kdaRatio = kdaRatio;
        this.kdRatio = kdRatio;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.headshotratio = headshotratio;
        this.headshots = headshots;
        this.bodyshots = bodyshots;
        this.legshots = legshots;
        this.score = score;
        this.acs = acs;
        this.tierIcon = tierIcon;
        this.agentIcon = agentIcon;
        this.tierColor = tierColor;
        this.MMR = MMR;
        this.damageMade = damageMade;
        this.adr = adr;
        this.matchTier = matchTier;
        this.rrChanges = rrChanges;
        this.matchID = matchID;
    }

    public int getMmr_change_to_last_game() {
        return mmr_change_to_last_game;
    }

    public void setMmr_change_to_last_game(int mmr_change_to_last_game) {
        this.mmr_change_to_last_game = mmr_change_to_last_game;
    }

    public String getRiotName() {
        return riotName;
    }

    public void setRiotName(String riotName) {
        this.riotName = riotName;
    }

    public String getRiotTag() {
        return riotTag;
    }

    public void setRiotTag(String riotTag) {
        this.riotTag = riotTag;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacterIcon() {
        return characterIcon;
    }

    public void setCharacterIcon(String characterIcon) {
        this.characterIcon = characterIcon;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getGame_start_patched() {
        return game_start_patched;
    }

    public void setGame_start_patched(String game_start_patched) {
        this.game_start_patched = game_start_patched;
    }

    public String getTeamSide() {
        return teamSide;
    }

    public void setTeamSide(String teamSide) {
        this.teamSide = teamSide;
    }

    public boolean isHas_won() {
        return has_won;
    }

    public void setHas_won(boolean has_won) {
        this.has_won = has_won;
    }

    public int getRounds_played() {
        return rounds_played;
    }

    public void setRounds_played(int rounds_played) {
        this.rounds_played = rounds_played;
    }

    public int getRounds_won() {
        return rounds_won;
    }

    public void setRounds_won(int rounds_won) {
        this.rounds_won = rounds_won;
    }

    public int getRounds_lost() {
        return rounds_lost;
    }

    public void setRounds_lost(int rounds_lost) {
        this.rounds_lost = rounds_lost;
    }

    public double getKdaRatio() {
        return kdaRatio;
    }

    public void setKdaRatio(double kdaRatio) {
        this.kdaRatio = kdaRatio;
    }

    public double getKdRatio() {
        return kdRatio;
    }

    public void setKdRatio(double kdRatio) {
        this.kdRatio = kdRatio;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public double getHeadshotratio() {
        return headshotratio;
    }

    public void setHeadshotratio(double headshotratio) {
        this.headshotratio = headshotratio;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public int getBodyshots() {
        return bodyshots;
    }

    public void setBodyshots(int bodyshots) {
        this.bodyshots = bodyshots;
    }

    public int getLegshots() {
        return legshots;
    }

    public void setLegshots(int legshots) {
        this.legshots = legshots;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getAcs() {
        return acs;
    }

    public void setAcs(double acs) {
        this.acs = acs;
    }

    public String getTierIcon() {
        return tierIcon;
    }

    public void setTierIcon(String tierIcon) {
        this.tierIcon = tierIcon;
    }

    public String getAgentIcon() {
        return agentIcon;
    }

    public void setAgentIcon(String agentIcon) {
        this.agentIcon = agentIcon;
    }

    public String getTierColor() {
        return tierColor;
    }

    public void setTierColor(String tierColor) {
        this.tierColor = tierColor;
    }

    public int getMMR() {
        return MMR;
    }

    public void setMMR(int MMR) {
        this.MMR = MMR;
    }

    public int getDamageMade() {
        return damageMade;
    }

    public void setDamageMade(int damageMade) {
        this.damageMade = damageMade;
    }

    public int getAdr() {
        return adr;
    }

    public void setAdr(int adr) {
        this.adr = adr;
    }

    public String getMatchTier() {
        return matchTier;
    }

    public void setMatchTier(String matchTier) {
        this.matchTier = matchTier;
    }

    public String getRrChanges() {
        return rrChanges;
    }

    public void setRrChanges(String rrChanges) {
        this.rrChanges = rrChanges;
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }
}
