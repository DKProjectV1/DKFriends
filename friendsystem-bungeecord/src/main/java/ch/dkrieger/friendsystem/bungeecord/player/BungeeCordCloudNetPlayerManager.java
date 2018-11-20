package ch.dkrieger.friendsystem.bungeecord.player;

import ch.dkrieger.friendsystem.bungeecord.player.online.ExternBungeeCordOnlinePlayer;
import ch.dkrieger.friendsystem.bungeecord.player.online.LocalBungeeCordOnlinePlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 19:48
 *
 */

public class BungeeCordCloudNetPlayerManager extends FriendPlayerManager {

    private Map<ProxiedPlayer, LocalBungeeCordOnlinePlayer> onlinePlayers;
    private Map<UUID, ExternBungeeCordOnlinePlayer> onlineCloudPlayers;

    public BungeeCordCloudNetPlayerManager() {
        this.onlinePlayers = new LinkedHashMap<>();
        this.onlineCloudPlayers = new LinkedHashMap<>();
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(UUID uuid) {
        OnlineFriendPlayer player = getOnlinePlayer(BungeeCord.getInstance().getPlayer(uuid));
        if(player == null) return getOnlinePlayer(CloudAPI.getInstance().getOnlinePlayer(uuid));
        return player;
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(String name) {
        OnlineFriendPlayer player = getOnlinePlayer(BungeeCord.getInstance().getPlayer(name));
        if(player == null){
            FriendPlayer friendPlayer = getPlayer(name);
            if(friendPlayer != null) player = getOnlinePlayer(CloudAPI.getInstance().getOnlinePlayer(friendPlayer.getUUID()));
        }
        return player;
    }
    public OnlineFriendPlayer getOnlinePlayer(ProxiedPlayer player){
        if(player != null){
            LocalBungeeCordOnlinePlayer online = this.onlinePlayers.get(player);
            if(online == null){
                online = new LocalBungeeCordOnlinePlayer(player);
                this.onlinePlayers.put(player,online);
            }
            return online;
        }
        return null;
    }
    public OnlineFriendPlayer getOnlinePlayer(CloudPlayer player){
        if(player != null){
            ExternBungeeCordOnlinePlayer online = this.onlineCloudPlayers.get(player.getUniqueId());
            if(online == null){
                online = new ExternBungeeCordOnlinePlayer(player);
                this.onlineCloudPlayers.put(player.getUniqueId(),online);
            }
            return online;
        }
        return null;
    }
}
