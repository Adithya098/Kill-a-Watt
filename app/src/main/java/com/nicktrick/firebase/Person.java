package com.nicktrick.firebase;

import org.parceler.Parcel;


@Parcel
public class Person {

    private String key;
    private String username;
    private String uniqid;
    private String mobile;
    private String email;
    private String usage;
    private String date;



    public Person(){

    }

    public Person(String username, String uniqid,String mobile,String email,String usage,String date){
        this.username = username;
        this.uniqid = uniqid;
        this.mobile = mobile;
        this.email = email;
        this.usage = usage;
        this.date = date;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!Person.class.isAssignableFrom(object.getClass()))
            return false;
        final Person person = (Person)object;
        return person.getKey().equals(key);
    }
}
