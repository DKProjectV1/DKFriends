package ch.dkrieger.friendsystem.lib.cloudnet;

import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyManager;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.utility.document.Document;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 22.11.18 19:50
 *
 */

public class CloudNetPartyManager extends PartyManager {

    @Override
    public void update(Party party) {
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","partySync"
                ,new Document().append("party",party));
        CloudAPI.getInstance().sendCustomSubServerMessage("DKFriends","partySync"
                ,new Document().append("party",party));
    }
}
