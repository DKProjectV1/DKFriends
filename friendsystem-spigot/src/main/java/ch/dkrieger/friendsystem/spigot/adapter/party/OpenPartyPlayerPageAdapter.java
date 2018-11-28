package ch.dkrieger.friendsystem.spigot.adapter.party;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 27.11.18 21:08
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.inventories.party.PartyPlayerPage;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class OpenPartyPlayerPageAdapter extends Adapter {

    public OpenPartyPlayerPageAdapter() {
        super("openPartyPlayerPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        Party party = FriendSystem.getInstance().getPartyManager().getParty(UUID.fromString((String) properties.get("partymember")));
        new PartyPlayerPage(player, party, party.getMember(UUID.fromString((String) properties.get("partymember")))).open();
    }
}