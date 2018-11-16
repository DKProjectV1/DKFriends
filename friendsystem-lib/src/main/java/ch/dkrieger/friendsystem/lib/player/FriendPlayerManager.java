package ch.dkrieger.friendsystem.lib.player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 18:47
 *
 */

import java.util.UUID;

public abstract class FriendPlayerManager {


    public FriendPlayer getPlayer(UUID uuid){

    }
    public FriendPlayer getPlayer(String name){

    }
    public FriendPlayer createPlayer(UUID uuid, String name){

    }
    public abstract OnlineFriendPlayer getOnlinePlayer(UUID uuid);

    public abstract OnlineFriendPlayer getOnlinePlayer(String name);
}
