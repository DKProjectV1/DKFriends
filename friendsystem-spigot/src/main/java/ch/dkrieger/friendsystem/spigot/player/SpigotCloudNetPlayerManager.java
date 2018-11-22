package ch.dkrieger.friendsystem.spigot.player;

import ch.dkrieger.friendsystem.lib.cloudnet.ExternCloudNetOnlinePlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 19:49
 *
 */

public class SpigotCloudNetPlayerManager extends FriendPlayerManager {

    private Map<UUID, ExternCloudNetOnlinePlayer> onlineCloudPlayers;

    public SpigotCloudNetPlayerManager() {
        this.onlineCloudPlayers = new LinkedHashMap<>();
    }
    @Override
    public List<OnlineFriendPlayer> getLoadedOnlinePlayers() {
        return new LinkedList<>(this.onlineCloudPlayers.values());
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(UUID uuid) {
        OnlineFriendPlayer player = getOnlinePlayer(Bukkit.getPlayer(uuid));
        if(player == null) return getOnlinePlayer(CloudAPI.getInstance().getOnlinePlayer(uuid));
        return player;
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(String name) {
        OnlineFriendPlayer player = getOnlinePlayer(Bukkit.getPlayer(name));
        if(player == null){
            FriendPlayer friendPlayer = getPlayer(name);
            if(friendPlayer != null) player = getOnlinePlayer(CloudAPI.getInstance().getOnlinePlayer(friendPlayer.getUUID()));
        }
        return player;
    }
    public OnlineFriendPlayer getOnlinePlayer(Player player){
        if(player != null) return this.onlineCloudPlayers.get(player.getUniqueId());
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