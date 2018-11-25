package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 21.11.18 19:57
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Map;

public class SettingsPage extends PrivateGUI {

    public SettingsPage(Player owner) {
        super("settings", owner);
        for(Map.Entry<Integer, ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack> entry : SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getDefaultInventoryItems().entrySet()) {
            getInventory().setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {

    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }
}