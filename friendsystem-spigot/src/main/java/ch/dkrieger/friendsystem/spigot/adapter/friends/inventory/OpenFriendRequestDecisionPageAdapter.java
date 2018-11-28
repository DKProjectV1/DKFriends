package ch.dkrieger.friendsystem.spigot.adapter.friends.inventory;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.inventories.friend.FriendRequestDecisionPage;
import org.bukkit.entity.Player;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 17:34
 *
 */

public class OpenFriendRequestDecisionPageAdapter extends Adapter {

    public OpenFriendRequestDecisionPageAdapter() {
        super("openFriendRequestDecisionPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        new FriendRequestDecisionPage(player, FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getFriend((String) properties.get("friend"))).open();
    }
}