package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:43
 *
 */

import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.UUID;

public class SpigotFriendPlayerManager extends FriendPlayerManager {


    @Override
    public OnlineFriendPlayer getOnlinePlayer(UUID uuid) {
        return null;
    }

    @Override
    public OnlineFriendPlayer getOnlinePlayer(String name) {
        return null;
    }
}