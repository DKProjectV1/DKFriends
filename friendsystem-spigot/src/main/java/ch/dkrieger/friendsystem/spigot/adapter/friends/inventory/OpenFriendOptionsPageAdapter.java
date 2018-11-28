package ch.dkrieger.friendsystem.spigot.adapter.friends.inventory;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.inventories.friend.FriendOptionsPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 17:34
 *
 */

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