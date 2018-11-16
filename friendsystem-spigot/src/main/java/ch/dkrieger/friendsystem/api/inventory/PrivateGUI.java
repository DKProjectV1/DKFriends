package ch.dkrieger.friendsystem.api.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 15:45
 *
 */

import org.bukkit.entity.Player;

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