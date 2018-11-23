package ch.dkrieger.friendsystem.bungeecord.player;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.cloudnet.ExternCloudNetOnlinePlayer;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.utility.document.Document;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 19:48
 *
 */

public class BungeeCordCloudNetPlayerManager extends FriendPlayerManager {

    private Map<ProxiedPlayer, LocalBungeeCordOnlinePlayer> onlinePlayers;
    private Map<UUID, ExternCloudNetOnlinePlayer> onlineCloudPlayers;

    public BungeeCordCloudNetPlayerManager() {
        this.onlinePlayers = new LinkedHashMap<>();
        this.onlineCloudPlayers = new LinkedHashMap<>();
    }

    @Override
    public List<OnlineFriendPlayer> getLoadedOnlinePlayers() {
        List<OnlineFriendPlayer> player = new LinkedList<>();
        player.addAll(this.onlineCloudPlayers.values());
        player.addAll(this.onlineCloudPlayers.values());
        return player;
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
    @Override
    public void updatePlayerSync(FriendPlayer player) {
        CloudAPI.getInstance().sendCustomSubServerMessage("DKFriends","updatePlayer"
                ,new Document().append("uuid",player.getUUID()));
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","updatePlayer"
                ,new Document().append("uuid",player.getUUID()));
    }
    @Override
    public void removeFromCache(UUID uuid) {
        this.onlineCloudPlayers.remove(uuid);
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
            ExternCloudNetOnlinePlayer online = this.onlineCloudPlayers.get(player.getUniqueId());
            if(online == null){
                online = new ExternCloudNetOnlinePlayer(player);
                this.onlineCloudPlayers.put(player.getUniqueId(),online);
            }
            return online;
        }
        return null;
    }
}
