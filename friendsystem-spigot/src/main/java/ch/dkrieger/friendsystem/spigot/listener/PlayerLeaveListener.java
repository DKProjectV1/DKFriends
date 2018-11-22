package ch.dkrieger.friendsystem.spigot.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 21.11.18 19:20
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerBedLeaveEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().removeProfile(event.getPlayer());
    }
}