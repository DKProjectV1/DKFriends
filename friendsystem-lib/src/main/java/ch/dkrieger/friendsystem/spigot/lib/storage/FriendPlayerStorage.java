package ch.dkrieger.friendsystem.spigot.lib.storage;

import ch.dkrieger.friendsystem.spigot.lib.player.FriendPlayer;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public interface FriendPlayerStorage {

    public FriendPlayer getPlayer(UUID uuid);

    public FriendPlayer getPlayer(String name);

    public void savePlayer(FriendPlayer player);

}
