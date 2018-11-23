package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 21.11.18 19:57
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.PrivateGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class SettingsPage extends PrivateGUI {

    public SettingsPage(Player owner) {
        super("settings", owner);
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