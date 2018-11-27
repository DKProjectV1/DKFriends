package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:45
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import ch.dkrieger.friendsystem.spigot.inventories.FriendOptionsPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class OpenFriendOptionsPageAdapter extends FriendAdapter {

    public OpenFriendOptionsPageAdapter() {
        super("openFriendOptionsPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        Bukkit.getScheduler().runTask(SpigotFriendSystemBootstrap.getInstance(), ()-> new FriendOptionsPage(player, FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getFriend((String) properties.get("friend"))).open());
    }
}