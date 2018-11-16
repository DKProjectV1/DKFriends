package ch.dkrieger.friendsystem.spigot.api.inventory;

import org.bukkit.entity.Player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public abstract class PrivateGUI extends GUI {

    private Player owner;

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