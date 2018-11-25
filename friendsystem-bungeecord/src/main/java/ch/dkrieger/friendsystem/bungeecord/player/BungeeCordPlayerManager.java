package ch.dkrieger.friendsystem.bungeecord.player;

import ch.dkrieger.friendsystem.bungeecord.party.BungeeCordPartyManager;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.Document;
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

    @Override
    public void updatePlayerSync(FriendPlayer player) {
        BungeeCordPartyManager.sendToAllSpigotServers("updatePlayer",new Document().append("uuid",player.getUUID()));
    }

    @Override
    public void removeFromCache(UUID uuid) {
        this.onlinePlayers.remove(BungeeCord.getInstance().getPlayer(uuid));
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
    public void syncOnlinePlayer(ProxiedPlayer player){
        if(player == null) return;
        BungeeCordPartyManager.sendToAllSpigotServers("syncOnlinePlayer"
                ,new Document().append("uuid",player.getUniqueId()).append("name",player.getName()).append("server",player.getServer()));
    }
    public void unregisterOnlinePlayer(UUID uuid){
        BungeeCordPartyManager.sendToAllSpigotServers("playerLogout",new Document().append("uuid",uuid));
    }
}
