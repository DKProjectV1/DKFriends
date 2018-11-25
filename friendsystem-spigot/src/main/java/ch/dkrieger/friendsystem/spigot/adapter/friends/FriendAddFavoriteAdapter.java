package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:36
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FriendAddFavoriteAdapter extends FriendAdapter {

    public FriendAddFavoriteAdapter(String name) {
        super("addFriendAsFavorite");
    }

    @Override
    public void execute(Player player, Object... objects) {
        FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getFriend(UUID.fromString((String) objects[0])).setFavorite(true);

    }
}