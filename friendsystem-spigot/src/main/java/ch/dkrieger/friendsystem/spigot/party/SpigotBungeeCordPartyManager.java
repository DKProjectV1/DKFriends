package ch.dkrieger.friendsystem.spigot.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 22.11.18 19:33
 *
 */

import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyManager;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;

public class SpigotBungeeCordPartyManager extends PartyManager {

    @Override
    public void update(Party party) {
        SpigotFriendSystemBootstrap.getInstance().getBungeeCordConnection()
                .send("syncParty",new Document().append("party",party));
    }
}
