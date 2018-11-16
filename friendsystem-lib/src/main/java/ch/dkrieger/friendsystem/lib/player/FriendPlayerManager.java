package ch.dkrieger.friendsystem.lib.player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 18:47
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class FriendPlayerManager {

    private List<FriendPlayer> loadedPlayers;

    public FriendPlayerManager() {
        this.loadedPlayers = new ArrayList<>();
    }

    public FriendPlayer getPlayer(UUID uuid){

    }
    public FriendPlayer getPlayer(String name){

    }
    public FriendPlayer createPlayer(UUID uuid, String name){
        FriendPlayer player = new FriendPlayer(uuid,name, Messages.PLAYER_DEFAULT_COLOR,null);
        this.loadedPlayers.add(player);
        FriendSystem.getInstance().getStorage().createPlayer(player);
        return player;
    }
    public abstract OnlineFriendPlayer getOnlinePlayer(UUID uuid);

    public abstract OnlineFriendPlayer getOnlinePlayer(String name);
}
