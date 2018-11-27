package ch.dkrieger.friendsystem.spigot.adapter.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 21:06
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpenInventoryAdapter extends Adapter {

    public OpenInventoryAdapter() {
        super("openMainInventory");
    }

    public void execute(Player player, Map<String, Object> properties) {
        player.openInventory(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(((String)properties.get("mainInventory")).split("[)(]")[1]).toBukkitInventory());
    }
}