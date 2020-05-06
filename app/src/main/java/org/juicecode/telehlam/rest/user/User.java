package org.juicecode.telehlam.rest.user;

import androidx.annotation.Nullable;

public class User {
    private long id;
    private String login;
    private String name;
    private @Nullable String surname;

    public User(long id, String login, String name, @Nullable String surname) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    public void setSurname(@Nullable String surname) {
        this.surname = surname;
    }

}
