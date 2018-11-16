package ch.dkrieger.friendsystem.lib.storage;

import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.Document;

import java.util.List;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public interface FriendStorage {

    public void connect();

    public void disconnect();

    public FriendPlayer getPlayer(UUID uuid) throws Exception;

    public FriendPlayer getPlayer(String name) throws Exception;

    public void createPlayer(FriendPlayer player);

    public void savePlayer(FriendPlayer player);

    public void saveFriends(UUID uuid, List<Friend> friends);

    public void saveRequests(UUID uuid, List<Friend> requests);

    public void saveSettings(UUID uuid,FriendPlayer.Settings settings);

    public void saveProperties(UUID uuid, Document properties);

    public void saveStatus(UUID uuid, FriendPlayer.Status status);

    public void saveHiderStatus(UUID uuid,FriendPlayer.HiderStatus hiderStatus);

}
