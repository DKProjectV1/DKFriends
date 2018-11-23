package ch.dkrieger.friendsystem.spigot.adapter;

import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:29
 *
 */

public abstract class FriendAdapter extends Adapter {

    public FriendAdapter(String name) {
        super(name);
    }

    public abstract void execute(Player player, Object... objects);
}