package ch.dkrieger.friendsystem.lib.storage;

import ch.dkrieger.friendsystem.lib.config.Config;
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

    public boolean connect();

    public void disconnect();

    public boolean isConnected();

    public FriendPlayer getPlayer(UUID uuid) throws Exception;

    public FriendPlayer getPlayer(String name) throws Exception;

    public void createPlayer(FriendPlayer player);

    public void savePlayer(FriendPlayer player);

    public void saveName(UUID uuid, String name);

    public void saveColor(UUID uuid, String color);

    public void saveGameProfile(UUID uuid, String gameProfile);

    public void saveLastLogin(UUID uuid, long lastLogin);

    public void saveMaxFriends(UUID uuid, int maxFriends);

    public void saveMaxPartyPlayers(UUID uuid, int maxPartyPlayers);

    public void saveMaxClanPlayers(UUID uuid, int maxClanPlayers);

    public void saveHiderStatus(UUID uuid,FriendPlayer.HiderStatus hiderStatus);

    public void saveStatus(UUID uuid, FriendPlayer.Status status);

    public void saveSettings(UUID uuid,FriendPlayer.Settings settings);

    public void saveFriends(UUID uuid, List<Friend> friends);

    public void saveRequests(UUID uuid, List<Friend> requests);

    public void saveFriendsAndRequests(UUID uuid, List<Friend> friends,List<Friend> requests);

    public void saveProperties(UUID uuid, Document properties);

}