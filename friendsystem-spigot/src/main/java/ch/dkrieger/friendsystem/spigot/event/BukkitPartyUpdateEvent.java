package ch.dkrieger.friendsystem.spigot.event;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 25.11.18 16:02
 *
 */

import ch.dkrieger.friendsystem.lib.party.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitPartyUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Party party;

    public BukkitPartyUpdateEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
