package com.example.valostats.Model;

public class Icon {
    private String tierIcon;
    private String tierColor;
    private String agentIcon;

    public Icon() {

    }

    public Icon(String tierIcon, String tierColor, String agentIcon) {
        this.tierIcon = tierIcon;
        this.tierColor = tierColor;
        this.agentIcon = agentIcon;
    }

    public String getTierIcon() {
        return tierIcon;
    }

    public void setTierIcon(String tierIcon) {
        this.tierIcon = tierIcon;
    }

    public String getTierColor() {
        return tierColor;
    }

    public void setTierColor(String tierColor) {
        this.tierColor = tierColor;
    }

    public String getAgentIcon() {
        return agentIcon;
    }

    public void setAgentIcon(String agentIcon) {
        this.agentIcon = agentIcon;
    }
}
