package ch.dkrieger.friendsystem.spigot.adapter;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 14:32
 *
 */

public abstract class Adapter {

    private String name;

    public Adapter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}