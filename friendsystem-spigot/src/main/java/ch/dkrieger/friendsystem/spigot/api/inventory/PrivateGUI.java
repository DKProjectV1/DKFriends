package ch.dkrieger.friendsystem.spigot.api.inventory;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import org.bukkit.entity.Player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public abstract class PrivateGUI extends GUI {

    private Player owner;

    public PrivateGUI(String mainInventory, Player owner) {
        this(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(mainInventory), owner);
    }

    public PrivateGUI(MainInventory mainInventory, Player owner) {
        setMainInventory(mainInventory);
        this.owner = owner;
        setInventory(mainInventory.toBukkitInventory(this, FriendSystem.getInstance().getPlayerManager().getPlayer(owner.getUniqueId())));
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