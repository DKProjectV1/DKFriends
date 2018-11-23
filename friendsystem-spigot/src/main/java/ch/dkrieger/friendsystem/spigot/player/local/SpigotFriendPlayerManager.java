package ch.dkrieger.friendsystem.spigot.player.local;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 20:05
 *
 */

public class SpigotFriendPlayerManager extends FriendPlayerManager {

    private Map<Player,LocalSpigotOnlinePlayer> onlinePlayers;

    public SpigotFriendPlayerManager() {
        this.onlinePlayers = new LinkedHashMap<>();
    }
    @Override
    public Collection<OnlineFriendPlayer> getLoadedOnlinePlayers() {
        return new LinkedList<>(this.onlinePlayers.values());
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(UUID uuid) {
        return getOnlinePlayer(Bukkit.getPlayer(uuid));
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(String name) {
        return getOnlinePlayer(Bukkit.getPlayer(name));
    }
    public LocalSpigotOnlinePlayer getOnlinePlayer(Player player){
        if(player != null){
            LocalSpigotOnlinePlayer online = this.onlinePlayers.get(player);
            if(online == null){
                online = new LocalSpigotOnlinePlayer(player);
                this.onlinePlayers.put(player,online);
            }
            return online;
        }
        return null;
    }

    @Override
    public void updatePlayerSync(FriendPlayer player) {}
    @Override
    public void removeFromCache(UUID uuid) {}
}