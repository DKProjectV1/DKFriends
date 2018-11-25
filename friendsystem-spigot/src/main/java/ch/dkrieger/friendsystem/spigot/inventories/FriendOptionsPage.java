package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:24
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.UUID;

public class FriendOptionsPage extends PrivateGUI {

    private Friend friend;

    public FriendOptionsPage(Player owner, UUID friend) {
        super("friendOptions", owner);
        System.out.println("new FriendOptionsPage");
        System.out.println(friend);
        this.friend = FriendSystem.getInstance().getPlayerManager().getPlayer(owner.getUniqueId()).getFriend(friend);
        System.out.println(this.friend);
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