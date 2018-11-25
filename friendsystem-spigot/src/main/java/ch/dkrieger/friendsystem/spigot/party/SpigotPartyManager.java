package ch.dkrieger.friendsystem.spigot.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 22.11.18 19:34
 *
 */

import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyManager;
import ch.dkrieger.friendsystem.spigot.event.BukkitPartyUpdateEvent;
import org.bukkit.Bukkit;

public class SpigotPartyManager extends PartyManager {

    @Override
    public void update(Party party) {
        Bukkit.getPluginManager().callEvent(new BukkitPartyUpdateEvent(party));
    }
}
