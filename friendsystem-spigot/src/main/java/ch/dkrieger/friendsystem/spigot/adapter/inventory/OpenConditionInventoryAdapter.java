package ch.dkrieger.friendsystem.spigot.adapter.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 21:07
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

public class OpenConditionInventoryAdapter extends Adapter {

    public OpenConditionInventoryAdapter() {
        super("openConditionInventory");
    }

    public void execute(Player player, String mainInventory, String conditionInventory) {
        player.openInventory(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(mainInventory).getConditionInventory(conditionInventory).toBukkitInventory());
    }
}