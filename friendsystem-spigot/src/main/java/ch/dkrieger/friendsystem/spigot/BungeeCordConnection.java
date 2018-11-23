package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 22.11.18 19:35
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.utils.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.UUID;

public class BungeeCordConnection implements PluginMessageListener {

    private FriendSystem friendSystem;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        try{
            if(Bukkit.getOnlineMode()) return;
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            DataInputStream input = new DataInputStream(byteStream);
            Document document = Document.loadData(input.readUTF());
            if(document.getString("action").equalsIgnoreCase("syncData")){

            }else if(document.getString("action").equalsIgnoreCase("syncParty")){
                Party party = document.getObject("party",Party.class);
                if(party != null) this.friendSystem.getPartyManager().replaceParty(party);
            }else if(document.getString("action").equalsIgnoreCase("syncOnlinePlayer")){

            }else if(document.getString("action").equalsIgnoreCase("updatePlayer")){
                UUID uuid = document.getObject("uuid",UUID.class);
                if(uuid != null) FriendSystem.getInstance().getPlayerManager().removeFromCache(uuid);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    /*
    new Document().append("parties",getParties())
                    .append("onlinePlayers",FriendSystem.getInstance().getPlayerManager().getLoadedOnlinePlayers()));
     */
    public void send(String action, Document document){
        if(Bukkit.getOnlineMode()) return;
        document.append("action",action);
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            out.writeUTF(document.toJson());
            if(Bukkit.getOnlinePlayers().size() == 0){
                System.out.println(Messages.SYSTEM_PREFIX+"Updater: Could not send data to bungeecord.");
                return;
            }
            for(Player player : Bukkit.getOnlinePlayers()){
                player.sendPluginMessage(SpigotFriendSystemBootstrap.getInstance(),"DKFriends",b.toByteArray());
                return;
            }
        }catch (IOException exception){
            System.out.println(Messages.SYSTEM_PREFIX+"Updater: Could not send data to bungeecord.");
            System.out.println(Messages.SYSTEM_PREFIX+"Updater: Error - "+exception.getMessage());
        }
    }
}
