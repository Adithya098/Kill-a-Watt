package com.nicktrick;

public interface UserRepository {
    void addNewUser(FirestoreUserModel firestoreUserModel);

    void retrieveUser(String uniquecode,String phoneno,String Username,String email);
}
