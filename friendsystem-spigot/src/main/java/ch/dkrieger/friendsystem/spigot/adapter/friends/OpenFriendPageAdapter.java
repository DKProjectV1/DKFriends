package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:21
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpenFriendPageAdapter extends FriendAdapter {

    public OpenFriendPageAdapter() {
        super("openFriendPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        Bukkit.getScheduler().runTask(SpigotFriendSystemBootstrap.getInstance(), ()-> SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getFriendPage().open());
    }
}