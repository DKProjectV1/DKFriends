package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitSubChannelMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 23.11.18 22:08
 *
 */

public class CloudNetMessageChannelListener implements Listener {

    @EventHandler
    public void onReceive(BukkitSubChannelMessageEvent event){
        if(event.getChannel() != null && event.getMessage() != null && event.getDocument() != null
                && event.getChannel().equalsIgnoreCase("DKFriends")){
            if(event.getMessage().equalsIgnoreCase("partySync")){
                Party party = event.getDocument().getObject("party",Party.class);
                if(party != null) FriendSystem.getInstance().getPartyManager().replaceParty(party);
            }else if(event.getMessage().equalsIgnoreCase("updatePlayer")){
                UUID uuid = event.getDocument().getObject("uuid",UUID.class);
                if(uuid != null) FriendSystem.getInstance().getPlayerManager().removeFromCache(uuid);
            }
        }
    }
}
