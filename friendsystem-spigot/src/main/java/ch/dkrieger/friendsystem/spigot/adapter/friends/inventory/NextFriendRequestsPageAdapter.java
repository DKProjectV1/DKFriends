package ch.dkrieger.friendsystem.spigot.adapter.friends.inventory;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 17:34
 *
 */

public class NextFriendRequestsPageAdapter extends Adapter {

    public NextFriendRequestsPageAdapter() {
        super("nextFriendRequestsPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getFriendRequestsPage().nextPage();
    }
}