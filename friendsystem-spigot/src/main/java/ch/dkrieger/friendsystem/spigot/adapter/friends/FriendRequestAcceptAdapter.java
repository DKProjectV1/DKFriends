package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 26.11.18 19:24
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

public class FriendRequestAcceptAdapter extends Adapter {

    public FriendRequestAcceptAdapter() {
        super("acceptFriendRequest");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getOnlinePlayer().executeCommand("friend accept " + properties.get("friend"));
    }
}