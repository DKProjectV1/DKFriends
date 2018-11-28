package ch.dkrieger.friendsystem.spigot.adapter.friends.inventory;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 17:34
 *
 */

public class OpenFriendRequestsPageAdapter extends Adapter {

    public OpenFriendRequestsPageAdapter() {
        super("openFriendRequestsPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        Bukkit.getScheduler().runTask(SpigotFriendSystemBootstrap.getInstance(), ()-> SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getFriendRequestsPage().open());
    }
}