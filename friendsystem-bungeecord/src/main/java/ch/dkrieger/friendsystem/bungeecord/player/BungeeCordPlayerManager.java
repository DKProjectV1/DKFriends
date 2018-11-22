package ch.dkrieger.friendsystem.bungeecord.player;

import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 19:48
 *
 */

public class BungeeCordPlayerManager extends FriendPlayerManager {

    private Map<ProxiedPlayer, LocalBungeeCordOnlinePlayer> onlinePlayers;

    public BungeeCordPlayerManager() {
        this.onlinePlayers = new LinkedHashMap<>();
    }

    @Override
    public List<OnlineFriendPlayer> getLoadedOnlinePlayers() {
        return new LinkedList<>(this.onlinePlayers.values());
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
            LocalBungeeCordOnlinePlayer online = this.onlinePlayers.get(player);
            if(online == null){
                online = new LocalBungeeCordOnlinePlayer(player);
                this.onlinePlayers.put(player,online);
            }
            return online;
        }
        return null;
    }
}
