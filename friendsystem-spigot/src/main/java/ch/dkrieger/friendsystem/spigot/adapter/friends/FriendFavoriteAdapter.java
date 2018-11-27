package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:36
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

public class FriendFavoriteAdapter extends Adapter {

    public FriendFavoriteAdapter() {
        super("favoriteFriend");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getOnlinePlayer().executeCommand("friend favorite " + properties.get("friend"));
    }
}