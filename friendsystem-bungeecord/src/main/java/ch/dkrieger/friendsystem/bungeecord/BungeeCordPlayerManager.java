package ch.dkrieger.friendsystem.bungeecord;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 11:47
 *
 */

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class BungeeCordPlayerManager extends FriendPlayerManager {

    private Map<ProxiedPlayer,BungeeCordOnlinePlayer> onlinePlayers;

    public BungeeCordPlayerManager() {
        this.onlinePlayers = new LinkedHashMap<>();
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(UUID uuid) {
        return getOnlinePlayer(BungeeCord.getInstance().getPlayer(uuid));
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(String name) {
       return getOnlinePlayer(BungeeCord.getInstance().getPlayer(name));
    }
    public OnlineFriendPlayer getOnlinePlayer(ProxiedPlayer player){
        if(player != null){
            BungeeCordOnlinePlayer online = this.onlinePlayers.get(player);
            if(online == null){
                online = new BungeeCordOnlinePlayer(player);
                this.onlinePlayers.put(player,online);
            }
            return online;
        }
        return null;
    }
    private class BungeeCordOnlinePlayer implements OnlineFriendPlayer{

        private ProxiedPlayer proxiedPlayer;

        public BungeeCordOnlinePlayer(ProxiedPlayer proxiedPlayer) {
            this.proxiedPlayer = proxiedPlayer;
        }
        @Override
        public UUID getUUID() {
            return this.proxiedPlayer.getUniqueId();
        }
        @Override
        public String getName() {
            return this.proxiedPlayer.getName();
        }
        @Override
        public String getServer() {
            return this.proxiedPlayer.getServer().getInfo().getName();
        }
        @Override
        public void sendMessage(String message) {
            sendMessage(new TextComponent(message));
        }
        @Override
        public void sendMessage(TextComponent component) {
            this.proxiedPlayer.sendMessage(component);
        }
        @Override
        public void connect(String server) {
            ServerInfo info = BungeeCord.getInstance().getServerInfo(server);
            if(info != null ){
                if(!this.proxiedPlayer.getServer().getInfo().equals(info)) this.proxiedPlayer.connect(info);
                else sendMessage(Messages.SERVER_ALREADY.replace("[prefix]",Messages.PREFIX_FRIEND));
            }else sendMessage(Messages.SERVER_NOTFOUND.replace("[prefix]",Messages.PREFIX_FRIEND));
        }
    }
}
