package ch.dkrieger.friendsystem.spigot.adapter;

import org.bukkit.entity.Player;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:29
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

    public abstract void execute(Player player, Map<String, Object> properties);
}