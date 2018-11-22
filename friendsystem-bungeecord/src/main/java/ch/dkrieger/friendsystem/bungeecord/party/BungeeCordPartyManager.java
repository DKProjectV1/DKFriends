package ch.dkrieger.friendsystem.bungeecord.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 18:56
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyManager;
import ch.dkrieger.friendsystem.lib.utils.Document;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;

public class BungeeCordPartyManager extends PartyManager implements Listener {

    @Override
    public void update(Party party) {
        sendToAllSpigotServers("syncParty",new Document().append("party",party));
    }
    @EventHandler
    public void onServerConnected(ServerConnectedEvent event){
        if(event.getServer().getInfo().getPlayers().size() <= 0){
            sendToAllSpigotServers("syncData",new Document().append("parties",getParties())
                    .append("onlinePlayers",FriendSystem.getInstance().getPlayerManager().getLoadedOnlinePlayers()));
        }
    }
    public static void sendToAllSpigotServers(String action, Document document){
        sendToAllSpigotServers(action,document,null);
    }
    public static void sendToAllSpigotServers(String action, Document document, String fromServer){
        for(ServerInfo server : BungeeCord.getInstance().getServers().values()) {
            if(fromServer != null && !(server.getName().equalsIgnoreCase(fromServer))) sendToSpigotServer(server,action,document);
        }
    }
    public static void sendToSpigotServer(ServerInfo server,String action, Document document){
        document.append("action",action);
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(byteStream);

            output.writeUTF(document.toJson());
            server.sendData("DKFriends",byteStream.toByteArray());
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
