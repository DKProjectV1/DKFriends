package ch.dkrieger.friendsystem.bungeecord.listeners;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 19:56
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import de.dytanic.cloudnet.bridge.event.proxied.ProxiedSubChannelMessageEvent;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class CloudNetMessageChannelListener implements Listener {

    @EventHandler
    public void onReceive(ProxiedSubChannelMessageEvent event){
        if(event.getChannel() != null && event.getMessage() != null && event.getDocument() != null
                && event.getChannel().equalsIgnoreCase("DKFriends")){
            if(event.getMessage().equalsIgnoreCase("sendMessage")){
                ProxiedPlayer player = BungeeCord.getInstance().getPlayer(event.getDocument().getObject("player", UUID.class));
                if(player != null) player.sendMessage(event.getDocument().getObject("message", TextComponent.class));
            }else if(event.getMessage().equalsIgnoreCase("privateMessage")){
                ProxiedPlayer player = BungeeCord.getInstance().getPlayer(event.getDocument().getObject("player", UUID.class));
                if(player != null){
                    OnlineFriendPlayer online = FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(player.getUniqueId());
                    online.sendPrivateMessage(event.getDocument().getString("sender")
                            ,event.getDocument().getObject("message", TextComponent.class));
                }
            }else if(event.getMessage().equalsIgnoreCase("connect")){
                ProxiedPlayer player = BungeeCord.getInstance().getPlayer(event.getDocument().getObject("player", UUID.class));
                if(player != null){
                    ServerInfo server = BungeeCord.getInstance().getServerInfo(event.getDocument().getString("server"));
                    if(server != null && !(player.getServer().getInfo().equals(server))) player.connect(server);
                }
            }else if(event.getMessage().equalsIgnoreCase("executeCommand")){
                ProxiedPlayer player = BungeeCord.getInstance().getPlayer(event.getDocument().getObject("player", UUID.class));
                if(player != null) BungeeCord.getInstance().getPluginManager().dispatchCommand(player
                        ,event.getDocument().getString("command"));
            }else if(event.getMessage().equalsIgnoreCase("partySync")){
                Party party = event.getDocument().getObject("party",Party.class);
                if(party != null) FriendSystem.getInstance().getPartyManager().replaceParty(party);
            }else if(event.getMessage().equalsIgnoreCase("updatePlayer")){
                UUID uuid = event.getDocument().getObject("uuid",UUID.class);
                if(uuid != null) FriendSystem.getInstance().getPlayerManager().removeFromCache(uuid);
            }
        }
    }
}
