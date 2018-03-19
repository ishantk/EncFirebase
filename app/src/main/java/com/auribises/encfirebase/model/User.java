package com.auribises.encfirebase.model;

/**
 * Created by ishantkumar on 19/03/18.
 */

public class User {

    public String name;
    public String email;
    public String password;

    public User(){

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
