package com.nicktrick.usage;



import org.parceler.Parcel;


@Parcel
public class Usage {

    private String key;
    private String uniqid;
    private String usage;
    private String dater;
    public Usage(){

    }

    public Usage(String uniqid, String usage, String dater) {

        this.uniqid = uniqid;
        this.usage = usage;
        this.dater = dater;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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







    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!Usage.class.isAssignableFrom(object.getClass()))
            return false;
        final Usage usage = (Usage)object;
        return usage.getKey().equals(key);
        //here i changed key to uniqid
    }
}
