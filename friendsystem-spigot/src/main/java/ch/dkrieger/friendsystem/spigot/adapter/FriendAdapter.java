package ch.dkrieger.friendsystem.spigot.adapter;

import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:29
 *
 */

public abstract class FriendAdapter extends Adapter {

    public FriendAdapter(String name) {
        super(name);
    }

    public abstract void execute(Player player, Map<String, Object> properties);
}