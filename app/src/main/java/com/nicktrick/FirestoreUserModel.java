package com.nicktrick;

public class FirestoreUserModel {
    String uniquecode="",phonenum="",username="", password ="";
    private String key;


    public FirestoreUserModel(String uniquecode, String phonenum, String username, String password) {
        this.uniquecode = uniquecode;
        this.phonenum = phonenum;
        this.username = username;
        this.password = password;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!FirestoreUserModel.class.isAssignableFrom(object.getClass()))
            return false;
        final FirestoreUserModel firestoreUserModel = (FirestoreUserModel) object;
        return firestoreUserModel.getKey().equals(key);
        //here i changed key to uniqid
    }
}
