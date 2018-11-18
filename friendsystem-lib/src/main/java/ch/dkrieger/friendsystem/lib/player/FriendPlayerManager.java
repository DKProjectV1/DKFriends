package ch.dkrieger.friendsystem.lib.player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 18:47
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import java.util.*;

public abstract class FriendPlayerManager {

    private Map<UUID,FriendPlayer> loadedPlayers;

    public FriendPlayerManager() {
        this.loadedPlayers = new HashMap<>();
    }
    public Collection<FriendPlayer> getLoadedPlayers() {
        return this.loadedPlayers.values();
    }
    public FriendPlayer getPlayer(UUID uuid) {
        System.out.println("getPlayer");
        FriendPlayer player = this.loadedPlayers.get(uuid);
        if(player != null) {
            System.out.println("getPlayer cache not null");
            return player;
        }
        System.out.println("getPlayerSave");
        try{
            return getPlayerSave(uuid);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    public FriendPlayer getPlayer(String name){
        try{
            Iterator<FriendPlayer> iterator = this.loadedPlayers.values().iterator();
            FriendPlayer player = null;
            while((player = iterator.next()) != null) if(player.getName().equalsIgnoreCase(name)) return player;
            return FriendSystem.getInstance().getStorage().getPlayer(name);
        }catch (Exception exception){
            return null;
        }
    }
    public FriendPlayer getPlayerSave(UUID uuid) throws Exception{
        System.out.println("getPlayerSave");
        FriendPlayer player = FriendSystem.getInstance().getStorage().getPlayer(uuid);
        System.out.println("player: " + player);
        if(player != null) this.loadedPlayers.put(player.getUUID(),player);
        return player;
    }
    public FriendPlayer createPlayer(UUID uuid, String name, String color, String gameProfile){
        System.out.println("createPlayer");
        FriendPlayer player = new FriendPlayer(uuid,name,color,gameProfile);
        this.loadedPlayers.put(player.getUUID(),player);
        FriendSystem.getInstance().getStorage().createPlayer(player);
        return player;
    }
    public abstract OnlineFriendPlayer getOnlinePlayer(UUID uuid);

    public abstract OnlineFriendPlayer getOnlinePlayer(String name);
}
