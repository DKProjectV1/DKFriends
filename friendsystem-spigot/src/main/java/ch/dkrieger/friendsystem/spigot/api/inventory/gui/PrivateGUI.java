package ch.dkrieger.friendsystem.spigot.api.inventory.gui;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import org.bukkit.entity.Player;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 24.11.18 12:33
 *
 */

public abstract class PrivateGUI extends GUI {

    private Player owner;

    public PrivateGUI(String configInventory, Player owner) {
        this(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(configInventory), owner);
    }

    public PrivateGUI(ConfigInventory configInventory, Player owner) {
        super(configInventory);
        this.owner = owner;
    }

    public PrivateGUI(String name, int size, Player owner) {
        super(name, size);
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void open(){
        open(this.owner);
    }
}