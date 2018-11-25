package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.spigot.event.BukkitFriendPlayerLogoutEvent;
import ch.dkrieger.friendsystem.spigot.event.BukkitFriendPlayerUpdateEvent;
import ch.dkrieger.friendsystem.spigot.event.BukkitOnlineFriendPlayerUpdateEvent;
import ch.dkrieger.friendsystem.spigot.event.BukkitPartyUpdateEvent;
import ch.dkrieger.friendsystem.spigot.player.SpigotCloudNetPlayerManager;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitPlayerDisconnectEvent;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitPlayerUpdateEvent;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitSubChannelMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 23.11.18 22:08
 *
 */

public class CloudNetListener implements Listener {

    @EventHandler
    public void onReceive(BukkitSubChannelMessageEvent event){
        if(event.getChannel() != null && event.getMessage() != null && event.getDocument() != null
                && event.getChannel().equalsIgnoreCase("DKFriends")){
            if(event.getMessage().equalsIgnoreCase("partySync")){
                Party party = event.getDocument().getObject("party",Party.class);
                if(party != null){
                    FriendSystem.getInstance().getPartyManager().replaceParty(party);
                    Bukkit.getPluginManager().callEvent(new BukkitPartyUpdateEvent(party));
                }
            }else if(event.getMessage().equalsIgnoreCase("updatePlayer")){
                UUID uuid = event.getDocument().getObject("uuid",UUID.class);
                if(uuid != null){
                    FriendSystem.getInstance().getPlayerManager().removeFromCache(uuid);
                    Bukkit.getPluginManager().callEvent(new BukkitFriendPlayerUpdateEvent(uuid));
                }
            }
        }
    }
    @EventHandler
    public void CloudPlayerUpdate(BukkitPlayerUpdateEvent event){
        Bukkit.getPluginManager().callEvent(new BukkitOnlineFriendPlayerUpdateEvent(event.getCloudPlayer().getUniqueId(),
                ((SpigotCloudNetPlayerManager)FriendSystem.getInstance().getPlayerManager()).getOnlinePlayer(event.getCloudPlayer())));
    }
    @EventHandler
    public void CloudPlayerLogout(BukkitPlayerDisconnectEvent event){
        Bukkit.getPluginManager().callEvent(new BukkitFriendPlayerLogoutEvent(event.getCloudPlayer().getUniqueId()));
    }
}
