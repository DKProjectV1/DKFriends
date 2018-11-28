package ch.dkrieger.friendsystem.spigot.inventories.party;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 13:56
 *
 */

public class PartyPlayerPage extends PrivateGUI {

    public PartyPlayerPage(Player owner, Party party, PartyMember partyMember) {
        super("partyPlayer", owner, partyMember,
                (party.canIntegrate(owner.getUniqueId(), partyMember.getUUID()) ? "canIntegrate" : null),
                (party.canIntegrate(owner.getUniqueId(), partyMember.getUUID()) && !partyMember.isLeader()) ? "canPromote" : null,
                (party.canIntegrate(owner.getUniqueId(), partyMember.getUUID()) && (partyMember.isLeader() || partyMember.isModerator())) ? "canDemote" : null);
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_OPEN, (Player) event.getPlayer(), getConfigInventory()));
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.CLICK, (Player) event.getWhoClicked(), event.getCurrentItem()));
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_CLOSE, (Player) event.getPlayer(), getConfigInventory()));
    }
}