package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:45
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.inventories.friend.FriendOptionsPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpenFriendOptionsPageAdapter extends Adapter {

    public OpenFriendOptionsPageAdapter() {
        super("openFriendOptionsPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        System.out.println("openfriendoptions");
        System.out.println(properties);
        Bukkit.getScheduler().runTask(SpigotFriendSystemBootstrap.getInstance(), ()-> new FriendOptionsPage(player, FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getFriend((String) properties.get("friend"))).open());
    }
}