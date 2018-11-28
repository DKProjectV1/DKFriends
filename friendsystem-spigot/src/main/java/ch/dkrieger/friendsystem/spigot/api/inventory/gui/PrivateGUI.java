package ch.dkrieger.friendsystem.spigot.api.inventory.gui;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.lib.player.Friend;
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
        this(configInventory, owner, null);
    }

    public PrivateGUI(String configInventory, Player owner, Friend friend, String... conditions) {
        this(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(configInventory), owner, friend, conditions);
    }

    public PrivateGUI(String configInventory, Player owner, Friend friend) {
        this(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(configInventory), owner, friend);
    }

    public PrivateGUI(String configInventory,  Player owner, PartyMember partyMember, String... conditions) {
        this(SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(configInventory), partyMember, owner, conditions);
    }

    public PrivateGUI(ConfigInventory configInventory, Player owner, Friend friend, String... conditions) {
        setConfigInventory(configInventory);
        setInventory(configInventory.clone()
                .setTitle(configInventory.getTitle()
                        .replace("[player]", FriendSystem.getInstance().getPlayerManager().getPlayer(owner.getUniqueId()).getColoredName())
                        .replace("[friend]", (friend == null ? "[friend]" : friend.getFriendPlayer().getColoredName())))
                .toBukkitInventory(this, friend, conditions));
        this.owner = owner;
    }

    public PrivateGUI(ConfigInventory configInventory, PartyMember partyMember, Player owner, String... conditions) {
        setConfigInventory(configInventory);
        setInventory(configInventory.clone()
                .setTitle(configInventory.getTitle()
                        .replace("[player]", FriendSystem.getInstance().getPlayerManager().getPlayer(owner.getUniqueId()).getColoredName())
                        .replace("[partymember]", (partyMember == null ? "[friend]" : partyMember.getPlayer().getColoredName())))
                .toBukkitInventory(this, partyMember, conditions));
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