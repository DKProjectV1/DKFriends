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

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        /*
        Bukkit.getScheduler().runTaskLater(SpigotFriendSystemBootstrap.getInstance(), () -> {
            System.out.println("open normal inventory");
            event.getPlayer().openInventory(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory("test1").toBukkitInventory());
            Bukkit.getScheduler().runTaskLater(SpigotFriendSystemBootstrap.getInstance(), () -> {
                System.out.println("open condition inventory");
                event.getPlayer().openInventory(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory("test1").getConditionInventory("ctest1").toBukkitInventory());
            }, 20*10);
        }, 20*10);*/
    }
}