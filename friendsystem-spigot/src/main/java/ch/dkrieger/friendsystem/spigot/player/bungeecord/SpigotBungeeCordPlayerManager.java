package ch.dkrieger.friendsystem.spigot.player.bungeecord;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 20:06
 *
 */

public class SpigotBungeeCordPlayerManager extends FriendPlayerManager {

    private Map<UUID,SpigotBungeeCordOnlinePlayer> onlinePlayers;

    public SpigotBungeeCordPlayerManager() {
        this.onlinePlayers = new LinkedHashMap<>();
    }
    @Override
    public Collection<OnlineFriendPlayer> getLoadedOnlinePlayers() {
        return new LinkedList<>(this.onlinePlayers.values());
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(UUID uuid) {
        return onlinePlayers.get(uuid);
    }
    @Override
    public OnlineFriendPlayer getOnlinePlayer(String name) {
        Iterator<SpigotBungeeCordOnlinePlayer> iterator = new ArrayList<>(this.onlinePlayers.values()).iterator();
        SpigotBungeeCordOnlinePlayer player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) return player;
        return null;
    }
    @Override
    public void updatePlayerSync(FriendPlayer player) {
        SpigotFriendSystemBootstrap.getInstance().getBungeeCordConnection().send("updatePlayer",new Document().append("uuid",player.getUUID()));
    }
    @Override
    public void removeFromCache(UUID uuid) {
        this.onlinePlayers.remove(uuid);
    }
    public void updateOnlinePlayers(SpigotBungeeCordOnlinePlayer player){
        this.onlinePlayers.put(player.getUUID(),player);
    }
    public void unregisterOnlinePlayer(UUID uuid){
        this.onlinePlayers.remove(uuid);
    }
}