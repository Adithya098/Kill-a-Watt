package com.nicktrick.usage;

public class Usagebean {
    private String uniqid;
    private String usage;
    private String dater;
    public Usagebean(){

    }
    public Usagebean(String uniqid, String usage, String dater) {
        this.uniqid = uniqid;
        this.usage = usage;
        this.dater = dater;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDater() {
        return dater;
    }

    public void setDater(String dater) {
        this.dater = dater;
    }


}
