package ch.dkrieger.friendsystem.lib.player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 18:47
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.storage.mongodb.MongoDBUtil;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class FriendPlayerManager {

    private List<FriendPlayer> loadedPlayers;

    public FriendPlayerManager() {
        this.loadedPlayers = new ArrayList<>();
    }
    public List<FriendPlayer> getLoadedPlayers() {
        return this.loadedPlayers;
    }
    public FriendPlayer getPlayer(UUID uuid){
        Iterator<FriendPlayer> iterator = this.loadedPlayers.iterator();
        FriendPlayer player = null;
        while((player = iterator.next()) != null) if(player.getUUID().equals(uuid)) return player;
        try{
            return getPlayerSave(uuid);
        }catch (Exception exception){}
        return null;
    }
    public FriendPlayer getPlayer(String name){
        try{
            Iterator<FriendPlayer> iterator = this.loadedPlayers.iterator();
            FriendPlayer player = null;
            while((player = iterator.next()) != null) if(player.getName().equalsIgnoreCase(name)) return player;
            return FriendSystem.getInstance().getStorage().getPlayer(name);
        }catch (Exception exception){
            return null;
        }
    }
    public FriendPlayer getPlayerSave(UUID uuid) throws Exception{
        FriendPlayer player = FriendSystem.getInstance().getStorage().getPlayer(uuid);
        if(player != null) this.loadedPlayers.add(player);
        System.out.println("getPlayerSave: "+player);
        return player;
    }
    public FriendPlayer createPlayer(UUID uuid, String name, String color, String gameProfile){
        FriendPlayer player = new FriendPlayer(uuid,name,color,gameProfile);
        this.loadedPlayers.add(player);
        FriendSystem.getInstance().getStorage().createPlayer(player);
        return player;
    }
    public abstract OnlineFriendPlayer getOnlinePlayer(UUID uuid);

    public abstract OnlineFriendPlayer getOnlinePlayer(String name);
}
