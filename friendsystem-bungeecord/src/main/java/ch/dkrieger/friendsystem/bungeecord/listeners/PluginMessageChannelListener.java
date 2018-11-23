package ch.dkrieger.friendsystem.bungeecord.listeners;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 19:22
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.Document;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class PluginMessageChannelListener implements Listener {

    @EventHandler
    public void onPluginMessageReceive(PluginMessageEvent event) {
        if(event.getTag().equalsIgnoreCase("DKFriends") && event.getSender() instanceof Server) {
            try{
                ByteArrayInputStream byteStream = new ByteArrayInputStream(event.getData());
                DataInputStream input = new DataInputStream(byteStream);
                Document document = Document.loadData(input.readUTF());

                if(document.getString("action").equalsIgnoreCase("syncOnlinePlayers")){


                }else if(document.getString("action").equalsIgnoreCase("sendMessage")){
                    ProxiedPlayer player = BungeeCord.getInstance().getPlayer(document.getObject("player", UUID.class));
                    if(player != null) player.sendMessage(document.getObject("message", TextComponent.class));
                }else if(document.getString("action").equalsIgnoreCase("sendPrivateMessage")){
                    ProxiedPlayer player = BungeeCord.getInstance().getPlayer(document.getObject("player", UUID.class));
                    if(player != null){
                        OnlineFriendPlayer online = FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(player.getUniqueId());
                        online.sendPrivateMessage(document.getString("sender"),document.getObject("message",TextComponent.class));
                    }
                }else if(document.getString("action").equalsIgnoreCase("connect")){
                    ProxiedPlayer player = BungeeCord.getInstance().getPlayer(document.getObject("player", UUID.class));
                    if(player != null){
                        ServerInfo server = BungeeCord.getInstance().getServerInfo(document.getString("server"));
                        if(server != null && !(player.getServer().getInfo().equals(server))) player.connect(server);
                    }
                }else if(document.getString("action").equalsIgnoreCase("syncParty")){
                    Party party = document.getObject("party",Party.class);
                    if(party != null) FriendSystem.getInstance().getPartyManager().replaceParty(party);
                }else if(document.getString("action").equalsIgnoreCase("updatePlayer")){
                    UUID uuid = document.getObject("uuid",UUID.class);
                    if(uuid != null) FriendSystem.getInstance().getPlayerManager().removeFromCache(uuid);
                }else if(document.getString("action").equalsIgnoreCase("executeCommand")){
                    ProxiedPlayer player = BungeeCord.getInstance().getPlayer(document.getObject("player", UUID.class));
                    if(player != null) BungeeCord.getInstance().getPluginManager().dispatchCommand(player
                            ,document.getString("command"));
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }
}
