package ch.dkrieger.friendsystem.spigot.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 15:40
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.LinkedHashMap;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(SpigotFriendSystemBootstrap.getInstance(), ()-> {
            SpigotFriendSystemBootstrap.getInstance().getInventoryManager().createProfile(event.getPlayer());
            Bukkit.getScheduler().runTaskLater(SpigotFriendSystemBootstrap.getInstance(), ()-> {
                SpigotFriendSystemBootstrap.getInstance().getAdapter("openFriendPage").execute(event.getPlayer(), new LinkedHashMap<>());
            },20*5);
        }, 20*5);
    }
}