package ch.dkrieger.friendsystem.spigot.adapter.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 21:06
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

public class OpenMainInventoryAdapter extends Adapter {


    public OpenMainInventoryAdapter() {
        super("openMainInventory");
    }

    public void execute(Player player, String mainInventory) {
        player.openInventory(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(mainInventory).toBukkitInventory());
    }
}